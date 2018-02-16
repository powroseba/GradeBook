package com.application.service;

import com.domain.dto.ExerciseDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExerciseService {

    List<ExerciseDTO> getTeacherExercises(HttpServletRequest request);
}
