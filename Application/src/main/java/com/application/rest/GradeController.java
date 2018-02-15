package com.application.rest;

import com.application.service.GradeService;
import com.domain.ExerciseAndGrades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public List<ExerciseAndGrades> getMyGrades(HttpServletRequest request) {
        return gradeService.findGradesForStudent(request);
    }
}
