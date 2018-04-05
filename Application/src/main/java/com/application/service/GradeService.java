package com.application.service;

import com.domain.AverageGradesOfStudent;
import com.domain.ExerciseAndGrades;
import com.entities.Exercises;
import com.entities.Grade;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GradeService {

    ExerciseAndGrades findGradesForStudent(HttpServletRequest request);

    void addGrade(HttpServletRequest request, Long studentId, Exercises exercises, Grade grade);

    List<AverageGradesOfStudent> getAverageGradesOfAllStudents();
}
