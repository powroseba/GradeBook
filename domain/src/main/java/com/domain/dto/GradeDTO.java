package com.domain.dto;

import com.entities.Exercise;
import com.entities.Grade;
import com.entities.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GradeDTO {

    private Long id;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Min(1)
    @Max(6)
    @NotNull
    private int grade;

    @Size(max = 256)
    private String description;

    private Student student;

    private Exercise exercise;

    public GradeDTO(Long id, Date date, int grade, String description, Student student, Exercise exercise) {
        this.id = id;
        this.date = date;
        this.grade = grade;
        this.description = description;
        this.student = student;
        this.exercise = exercise;
    }

    public static GradeDTO convert(Grade grade) {
        return new GradeDTO(grade.getId(), grade.getDate(), grade.getGrade(), grade.getDescription(),
                grade.getStudent(), grade.getExercise());
    }

    public static Grade convert(GradeDTO gradeDTO) {
        Grade grade = new Grade(gradeDTO.getDate(), gradeDTO.getGrade(), gradeDTO.getDescription());
        grade.setStudent(gradeDTO.getStudent());
        grade.setExercise(gradeDTO.getExercise());
        return grade;
    }
}
