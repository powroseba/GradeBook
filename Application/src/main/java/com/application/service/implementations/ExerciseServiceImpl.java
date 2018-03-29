package com.application.service.implementations;

import com.application.exceptions.notfound.ExerciseNotFoundException;
import com.application.exceptions.notfound.StudentNotFoundException;
import com.application.exceptions.notfound.TeacherNotFoundException;
import com.application.rest.ExerciseController;
import com.application.service.ExerciseService;
import com.application.tools.TokenUsernameParserService;
import com.domain.dto.ExerciseDTO;
import com.entities.Exercise;
import com.entities.Student;
import com.entities.Teacher;
import com.repositories.ExerciseRepository;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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

        return exerciseList.stream().map(ExerciseDTO::convert).peek(exerciseDTO -> {
            exerciseDTO.setTeacher(null);
            exerciseDTO.getSchoolClass().setTutor(null);
            exerciseDTO.setGrades(null);
            exerciseDTO.getStudents().clear();
        }).collect(Collectors.toList());
    }

    @Override
    public List<ExerciseDTO> getStudentExercises(HttpServletRequest request) {
        String username = tokenUsernameParserService.parseUsername(request);
        Student student = (Student)userModelRepository.findByUsername(username).getUserModelDetails();

        if (!Optional.ofNullable(student).isPresent()) {
            throw new StudentNotFoundException();
        }

        List<Exercise> exerciseList = exerciseRepository.findByStudents_Id(student.getId());
        if ((!Optional.ofNullable(exerciseList).isPresent()) || exerciseList.isEmpty()) {
            throw new ExerciseNotFoundException();
        }

        return exerciseList.stream().map(ExerciseDTO::convert).peek(exerciseDTO -> {
            exerciseDTO.getTeacher().setUserModel(null);
            exerciseDTO.getTeacher().setExercises(null);
            exerciseDTO.getTeacher().setSchoolClass(null);
            exerciseDTO.getSchoolClass().setTutor(null);
            exerciseDTO.getGrades().forEach(grade -> {
                grade.setExercise(null);
                grade.setStudent(null);
            });
            exerciseDTO.getStudents().clear();
            exerciseDTO.add(linkTo(methodOn(ExerciseController.class).getExercise(exerciseDTO.getExerciseId())).withRel("Exercise"));
        }).collect(Collectors.toList());
    }

    @Override
    public ExerciseDTO getExercise(Long id) {
        Exercise exercise = exerciseRepository.getOne(id);

        if (!Optional.ofNullable(exercise).isPresent()) {
            throw new ExerciseNotFoundException();
        }

        ExerciseDTO exerciseDTO = ExerciseDTO.convert(exercise);
        exerciseDTO.getTeacher().setUserModel(null);
        exerciseDTO.getTeacher().setExercises(null);
        exerciseDTO.getTeacher().setSchoolClass(null);
        exerciseDTO.getSchoolClass().setTutor(null);
        exerciseDTO.getGrades().forEach(g -> {
            g.setExercise(null);
            g.setStudent(null);
        });
        exerciseDTO.getStudents().clear();
        exerciseDTO.setSchoolClass(null);

        exerciseDTO.add(ControllerLinkBuilder.linkTo(ExerciseController.class).slash("student").withRel("Exercise"));
        exerciseDTO.add(ControllerLinkBuilder.linkTo(ExerciseController.class).slash("teacher").withRel("Exercise"));

        return exerciseDTO;
    }
}
