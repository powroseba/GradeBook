package com.application.rest;

import com.application.service.GradeService;
import com.application.service.implementations.RabbitProducer;
import com.domain.ExerciseAndGrades;
import com.entities.Exercises;
import com.entities.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/grades")
public class GradeController {

    private GradeService gradeService;

    private RabbitProducer rabbitProducer;

    @Autowired
    public GradeController(GradeService gradeService, RabbitProducer rabbitProducer) {
        this.gradeService = gradeService;
        this.rabbitProducer = rabbitProducer;
    }

    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ExerciseAndGrades getMyGrades(HttpServletRequest request) {
        return gradeService.findGradesForStudent(request);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addGrade(HttpServletRequest request, @RequestParam("studentId") Long studentId,
                         @RequestParam("exercise") Exercises exercises,
                         @RequestBody Grade grade) {
        gradeService.addGrade(request, studentId, exercises, grade);
    }

    @PostMapping(value = "/pdf")
    @PreAuthorize("hasRole('STUDENT')")
    public void sendPDFWithMyGrades(HttpServletRequest request) {
        rabbitProducer.sendGrades(request);
    }
}
