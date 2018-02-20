package com.application.service.implementations;

import com.application.exceptions.notfound.ExerciseNotFoundException;
import com.application.exceptions.notfound.TeacherNotFoundException;
import com.application.service.ExerciseService;
import com.application.tools.TokenUsernameParserService;
import com.domain.dto.ExerciseDTO;
import com.entities.Exercise;
import com.entities.Teacher;
import com.repositories.ExerciseRepository;
import com.repositories.UserModelRepository;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        if (!Optional.ofNullable(teacher).isPresent()) {
            throw new TeacherNotFoundException();
        }

        List<Exercise> exerciseList = exerciseRepository.findByTeacher(teacher);
        if ((!Optional.ofNullable(exerciseList).isPresent()) || exerciseList.isEmpty()) {
            throw new ExerciseNotFoundException();
        }

        List<ExerciseDTO> exerciseDTOList = new ArrayList<>();
        for (Exercise e : exerciseList) {
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
