package com.application.service;

import com.entities.Exercise;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ExerciseService {

    List<Exercise> getTeacherExercises(HttpServletRequest request);
}
