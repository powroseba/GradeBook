package com.application.service;

import com.domain.ExerciseAndGrades;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GradeService {

    List<ExerciseAndGrades> findGradesForStudent(HttpServletRequest request);
}
