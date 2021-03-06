package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "GRADE")
@NoArgsConstructor
@Getter
@Setter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Min(1)
    @Max(6)
    @NotNull
    private int grade;

    private String description;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "EXCERCISE_ID")
    private Exercise exercise;

    public Grade(Date date, int grade, String description) {
        this.date = date;
        this.grade = grade;
        this.description = description;
    }
}
