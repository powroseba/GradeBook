package com.application.rest;

import com.application.service.GradeService;
import com.domain.ExerciseAndGrades;
import com.entities.Exercises;
import com.entities.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/grades")
public class GradeController {

    private GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ExerciseAndGrades getMyGrades(HttpServletRequest request) {
        return gradeService.findGradesForStudent(request);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('TEACHER')")
    public void addGrade(HttpServletRequest request, @RequestParam("studentId") Long studentId,
                         @RequestParam("exercise") Exercises exercises,
                         @RequestBody Grade grade) {
        gradeService.addGrade(request, studentId, exercises, grade);
    }
}
