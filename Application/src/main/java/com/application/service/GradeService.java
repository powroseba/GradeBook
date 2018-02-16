package com.application.service;

import com.domain.ExerciseAndGrades;
import com.entities.Exercises;
import com.entities.Grade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GradeService {

    List<ExerciseAndGrades> findGradesForStudent(HttpServletRequest request);

    void addGrade(HttpServletRequest request, Long studentId, Exercises exercises, Grade grade);
}
