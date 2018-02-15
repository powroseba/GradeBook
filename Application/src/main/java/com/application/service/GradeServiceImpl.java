package com.application.service;

import com.application.tools.TokenUsernameParserService;
import com.domain.ExerciseAndGrades;
import com.entities.Student;
import com.entities.UserModel;
import com.repositories.StudentRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    @PersistenceContext
    private EntityManager em;
    private TokenUsernameParserService tokenUsernameParserService = new TokenUsernameParserService();

    private StudentRepository studentRepository;
    private UserModelRepository userModelRepository;

    @Autowired
    public GradeServiceImpl(StudentRepository studentRepository, UserModelRepository userModelRepository) {
        this.studentRepository = studentRepository;
        this.userModelRepository = userModelRepository;
    }

    @Override
    public List<ExerciseAndGrades> findGradesForStudent(HttpServletRequest request) {
        String username = tokenUsernameParserService.parseUsername(request);
        UserModel userModel = userModelRepository.findByUsername(username);
        Long id = ((Student)userModel.getUserModelDetails()).getId();
        System.out.println(id);


        Query query = em.createNativeQuery("SELECT exercise.name_of_exercise, grade.grade FROM grade inner join exercise on (grade.exercise_id = exercise.id) where grade.student_id = ?");
        List result = query.setParameter(1, id).getResultList();
        for(Object p:result) {
            Object[] fields = (Object[])p;
            System.out.println("Exercise: " + fields[0] + ", grades: " + fields[1]);
        }
        return null;
    }
}
