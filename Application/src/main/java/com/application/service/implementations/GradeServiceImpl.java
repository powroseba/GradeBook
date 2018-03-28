package com.application.service.implementations;

import com.application.exceptions.notfound.*;
import com.application.service.GradeService;
import com.application.tools.TokenUsernameParserService;
import com.domain.ExerciseAndGrades;
import com.entities.*;
import com.repositories.ExerciseRepository;
import com.repositories.GradeRepository;
import com.repositories.StudentRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        UserModel userModel = userModelRepository.findByUsername(username).get();

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserNotFoundException();
        }
        Long id = ((Student)userModel.getUserModelDetails()).getId();
        ExerciseAndGrades exerciseAndGrades = new ExerciseAndGrades(userModel.getEmail());


        Query query = em.createNativeQuery("SELECT exercise.name_of_exercise, grade.grade FROM grade inner join exercise on (grade.exercise_id = exercise.id) where grade.student_id = ?");
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
        return exerciseAndGrades;
    }

    @Override
    public void addGrade(HttpServletRequest request, Long studentId, Exercises exercises, Grade grade) {
        String username = tokenUsernameParserService.parseUsername(request);
        Teacher teacher = (Teacher) userModelRepository.findByUsername(username).get().getUserModelDetails();
        if (!Optional.ofNullable(teacher).isPresent()) {
            throw new TeacherNotFoundException();
        }
        if (!studentRepository.exists(studentId)) {
            throw new StudentNotFoundException();
        }
        Student student = studentRepository.findOne(studentId);

        Exercise exercise = exerciseRepository.findByTeacherAndSchoolClassAndNameOfExerciseAndStudentsIsContaining
                (teacher, student.getSchoolClass(), exercises, student).get();

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
}
