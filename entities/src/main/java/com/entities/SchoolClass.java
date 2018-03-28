package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Teacher tutor;

    @NotNull
    @Size(min = 1, max = 15)
    private String name;

    @Enumerated(EnumType.STRING)
    private Profile profile;

    @OneToMany(targetEntity = Exercise.class, fetch = FetchType.EAGER, mappedBy = "schoolClass")
    @JsonIgnore
    private Set<Exercise> exercises = new HashSet<>();

    @OneToMany(targetEntity = Student.class, fetch = FetchType.EAGER, mappedBy = "schoolClass")
    @JsonIgnore
    private Set<Student> students = new HashSet<>();

    public SchoolClass(String name, Profile profile) {
        this.name = name;
        this.profile = profile;
    }
}
