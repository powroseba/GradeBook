package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "EXCERCISE")
@Getter
@Setter
@NoArgsConstructor
public class Excercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "excercise")
    private Teacher teacher;

    private String nameOfExcercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SCHOOL_CLASS_ID")
    private SchoolClass schoolClass;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EXCERCISE_STUDENT",
            joinColumns = {@JoinColumn(name = "EXCERCISE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false) })
    private Set<Student> students;

    public Excercise(Teacher teacher, String nameOfExcercise) {
        this.teacher = teacher;
        this.nameOfExcercise = nameOfExcercise;
    }
}
