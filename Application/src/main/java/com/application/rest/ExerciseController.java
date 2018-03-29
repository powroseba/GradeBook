package com.application.rest;

import com.application.service.ExerciseService;
import com.domain.dto.ExerciseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/exercises")
public class ExerciseController {

    private ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping(value = "/teacher")
    @PreAuthorize("hasRole('TEACHER')")
    public List<ExerciseDTO> getTeacherExercises(HttpServletRequest request) {
        return exerciseService.getTeacherExercises(request);
    }

    @GetMapping(value = "/student")
    @PreAuthorize("hasRole('STUDENT')")
    public List<ExerciseDTO> getStudentExercises(HttpServletRequest request) {
        return exerciseService.getStudentExercises(request);
    }


    @GetMapping
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ExerciseDTO getExercise(@RequestParam("exerciseId") Long id) {
        return exerciseService.getExercise(id);
    }


}
