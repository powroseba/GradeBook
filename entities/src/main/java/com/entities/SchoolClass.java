package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SCHOOL_CLASS")
@Getter
@Setter
@NoArgsConstructor
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "schoolClass", targetEntity = Teacher.class)
    private Teacher tutor;

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    private Profile profile;

    @OneToMany(targetEntity = Exercise.class, fetch = FetchType.EAGER, mappedBy = "schoolClass")
    private Set<Exercise> exercises = new HashSet<>();

    @OneToMany(targetEntity = Student.class, fetch = FetchType.EAGER, mappedBy = "schoolClass")
    private Set<Student> students = new HashSet<>();

    public SchoolClass(String name, Profile profile) {
        this.name = name;
        this.profile = profile;
    }
}
