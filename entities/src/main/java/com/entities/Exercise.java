package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EXERCISE")
@Getter
@Setter
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Exercises nameOfExercise;

    @OneToMany(targetEntity = Grade.class, fetch = FetchType.EAGER, mappedBy = "exercise")
    @JsonIgnore
    private Set<Grade> grades = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SCHOOL_CLASS_ID")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private SchoolClass schoolClass;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EXERCISE_STUDENT",
            joinColumns = {@JoinColumn(name = "EXERCISE_ID") },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID") })
    @JsonIgnore
    private Set<Student> students  = new HashSet<>();

    public Exercise(Exercises nameOfExercise) {
        this.nameOfExercise = nameOfExercise;
    }
}
