package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @OneToMany(targetEntity = Excercise.class, fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private Set<Excercise> excercises;

    @OneToMany(targetEntity = Student.class, fetch = FetchType.LAZY, mappedBy = "schoolClass")
    private Set<Student> students;

    public SchoolClass(Teacher tutor, String name, Profile profile) {
        this.tutor = tutor;
        this.name = name;
        this.profile = profile;
    }
}
