package com.application.service.implementations;

import com.application.service.ExerciseService;
import com.application.tools.TokenUsernameParserService;
import com.domain.dto.ExerciseDTO;
import com.entities.Exercise;
import com.entities.Teacher;
import com.repositories.ExerciseRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
    public List<ExerciseDTO> getTeacherExercises(HttpServletRequest request) {
        String userName = tokenUsernameParserService.parseUsername(request);
        Teacher teacher = (Teacher)userModelRepository.findByUsername(userName).getUserModelDetails();
        List<ExerciseDTO> exerciseDTOList = new ArrayList<>();
        for (Exercise e : exerciseRepository.findByTeacher(teacher)) {
            ExerciseDTO exerciseDTO = ExerciseDTO.convert(e);
            exerciseDTO.setTeacher(null);
            exerciseDTO.getSchoolClass().setTutor(null);
            exerciseDTO.setGrades(null);
            exerciseDTO.getStudents().clear();

            exerciseDTOList.add(exerciseDTO);
        }
        return exerciseDTOList;
    }
}
