package com.application.service;

import com.application.exceptions.notfound.ExerciseNotFoundException;
import com.application.exceptions.notfound.StudentNotFoundException;
import com.application.exceptions.notfound.TeacherNotFoundException;
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
import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService{

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
    public List<ExerciseAndGrades> findGradesForStudent(HttpServletRequest request) {
        String username = tokenUsernameParserService.parseUsername(request);
        UserModel userModel = userModelRepository.findByUsername(username);
        Long id = ((Student)userModel.getUserModelDetails()).getId();
        List<ExerciseAndGrades> exerciseAndGradesList = new ArrayList<>();


        Query query = em.createNativeQuery("SELECT exercise.name_of_exercise, grade.grade FROM grade inner join exercise on (grade.exercise_id = exercise.id) where grade.student_id = ?");
        List result = query.setParameter(1, id).getResultList();
        for(Object p:result) {
            Object[] fields = (Object[])p;
            boolean exerciseExist = false;
            for (ExerciseAndGrades e : exerciseAndGradesList) {
                if (e.getExercise().equals(Exercises.valueOf(fields[0].toString()))) {
                    e.getGrades().add((Integer) fields[1]);
                    exerciseExist = true;
                }
            }
            if (!exerciseExist) {
                ExerciseAndGrades exerciseAndGrades = new ExerciseAndGrades();
                exerciseAndGrades.setExercise(Exercises.valueOf(fields[0].toString()));
                exerciseAndGrades.getGrades().add((Integer)fields[1]);
                exerciseAndGradesList.add(exerciseAndGrades);
            }
        }
        return exerciseAndGradesList;
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
}
