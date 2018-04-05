package com.application.service.implementations;

import com.application.exceptions.notfound.*;
import com.application.rest.GradeController;
import com.application.service.GradeService;
import com.application.tools.TokenUsernameParserService;
import com.domain.AverageGradesOfStudent;
import com.domain.ExerciseAndGrades;
import com.entities.*;
import com.repositories.ExerciseRepository;
import com.repositories.GradeRepository;
import com.repositories.StudentRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class GradeServiceImpl implements GradeService {

    @PersistenceContext
    private EntityManager em;
    private TokenUsernameParserService tokenUsernameParserService = new TokenUsernameParserService();

    private StudentRepository studentRepository;
    private UserModelRepository userModelRepository;
    private GradeRepository gradeRepository;
    private ExerciseRepository exerciseRepository;

    @Autowired
    public GradeServiceImpl(StudentRepository studentRepository, UserModelRepository userModelRepository,
                            GradeRepository gradeRepository, ExerciseRepository exerciseRepository) {
        this.studentRepository = studentRepository;
        this.userModelRepository = userModelRepository;
        this.gradeRepository = gradeRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public ExerciseAndGrades findGradesForStudent(HttpServletRequest request) {
        String username = tokenUsernameParserService.parseUsername(request);
        UserModel userModel = userModelRepository.findByUsername(username);

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserNotFoundException();
        }
        Long id = ((Student)userModel.getUserModelDetails()).getId();
        ExerciseAndGrades exerciseAndGrades = new ExerciseAndGrades(userModel.getEmail());


        Query query = em.createNativeQuery("SELECT exercise.name_of_exercise, grade.grade FROM" +
                " grade inner join exercise on (grade.exercise_id = exercise.id) where grade.student_id = ?");
        List result = query.setParameter(1, id).getResultList();

        if (result.isEmpty()) {
            throw new GradeNotFoundException();
        }
        for(Object p:result) {
            Object[] fields = (Object[])p;

            Exercises key = Exercises.valueOf(fields[0].toString());
            Integer grade = (Integer) fields[1];

            if (exerciseAndGrades.getExerciseAndGradeList().containsKey(key)) {
                ArrayList<Integer> newGrades = exerciseAndGrades.getExerciseAndGradeList().
                        get(key);

                newGrades.add(grade);

                exerciseAndGrades.getExerciseAndGradeList().put(key, newGrades);
            } else {
                ArrayList<Integer> gradeList = new ArrayList<Integer>();
                gradeList.add(grade);
                exerciseAndGrades.getExerciseAndGradeList().
                        put(key, gradeList);
            }
        }

        exerciseAndGrades.add(ControllerLinkBuilder.linkTo(GradeController.class).slash("/pdf")
                .withRel("Send all grades and exercises to email in PDF format"));

        return exerciseAndGrades;
    }

    @Override
    public void addGrade(HttpServletRequest request, Long studentId, Exercises exercises, Grade grade) {
        String username = tokenUsernameParserService.parseUsername(request);
        Teacher teacher = (Teacher) userModelRepository.findByUsername(username).getUserModelDetails();
        if (!Optional.ofNullable(teacher).isPresent()) {
            throw new TeacherNotFoundException();
        }
        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException();
        }
        Student student = studentRepository.findOne(studentId);

        Exercise exercise = exerciseRepository.findByTeacherAndSchoolClassAndNameOfExerciseAndStudentsIsContaining
                (teacher, student.getSchoolClass(), exercises, student);

        if (!Optional.ofNullable(exercise).isPresent()) {
            throw new ExerciseNotFoundException();
        }

        grade.setExercise(exercise);
        exercise.getGrades().add(grade);

        grade.setStudent(student);
        student.getGrades().add(grade);

        gradeRepository.save(grade);
        studentRepository.save(student);
        exerciseRepository.save(exercise);
    }


    public List<AverageGradesOfStudent> getAverageGradesOfAllStudents() {
        Query query = em.createNativeQuery("SELECT student.id, student.first_name, student.last_name, school_class.name, school_class.profile, avg(grade.grade)" +
                "from grade inner join student on (grade.student_id = student.id )" +
                "inner join school_class on (student.school_class_id = school_class.id)" +
                "group by student.id, school_class.name, school_class.profile order by avg desc");
        List result = query.getResultList();
        List<AverageGradesOfStudent> averageGradesOfStudentList = new ArrayList<>();

        for(Object p:result) {
            Object[] fields = (Object[]) p;

            averageGradesOfStudentList.add(AverageGradesOfStudent
                    .builder()
                    .studentId(Long.parseLong(fields[0].toString()))
                    .firstName(fields[1].toString())
                    .lastName(fields[2].toString())
                    .schoolClassName(fields[3].toString())
                    .schoolClassProfile(Profile.valueOf(fields[4].toString()))
                    .averageGrades(Float.parseFloat(fields[5].toString()))
                    .build());
        }
        return averageGradesOfStudentList;
    }
}
