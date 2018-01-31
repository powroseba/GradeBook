package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@Setter
@Getter
@NoArgsConstructor
public class Student extends UserModelDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull
    private Date yearOfBirth;

    @OneToOne(mappedBy = "student", targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @OneToMany(targetEntity = Grade.class, fetch = FetchType.LAZY, mappedBy = "student")
    private Set<Grade> grades = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SCHOOL_CLASS_ID")
    private SchoolClass schoolClass;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Exercise> exercises = new HashSet<>();

    public Student(String firstName, String lastName, Date yearOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
    }
}
