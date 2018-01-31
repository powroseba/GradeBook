package com.entities;

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

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "exercise")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Exercises nameOfExercise;

    @OneToMany(targetEntity = Grade.class, fetch = FetchType.LAZY, mappedBy = "exercise")
    private Set<Grade> grades = new HashSet<>();;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_CLASS_ID")
    private SchoolClass schoolClass;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EXERCISE_STUDENT",
            joinColumns = {@JoinColumn(name = "EXERCISE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) })
    private Set<Student> students  = new HashSet<>();

    public Exercise(Exercises nameOfExercise) {
        this.nameOfExercise = nameOfExercise;
    }
}
