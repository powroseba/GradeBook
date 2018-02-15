package com.application.service;

import com.application.tools.TokenUsernameParserService;
import com.entities.Exercise;
import com.entities.Teacher;
import com.repositories.ExerciseRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private TokenUsernameParserService tokenUsernameParserService = new TokenUsernameParserService();

    private ExerciseRepository exerciseRepository;
    private UserModelRepository userModelRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, UserModelRepository userModelRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userModelRepository = userModelRepository;
    }

    @Override
    public List<Exercise> getTeacherExercises(HttpServletRequest request) {
        String userName = tokenUsernameParserService.parseUsername(request);
        Teacher teacher = (Teacher)userModelRepository.findByUsername(userName).getUserModelDetails();
        return exerciseRepository.findByTeacher(teacher);
    }
}
