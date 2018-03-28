package com.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(max = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "EXERCISE_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Exercise exercise;

    public Grade(Date date, int grade, String description) {
        this.date = date;
        this.grade = grade;
        this.description = description;
    }
}
