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
    @Size(min = 3, max = 15)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToOne(mappedBy = "student", targetEntity = UserModel.class)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @OneToMany(targetEntity = Grade.class, mappedBy = "student")
    private Set<Grade> grades = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "SCHOOL_CLASS_ID")
    private SchoolClass schoolClass;

    @ManyToMany(mappedBy = "students")
    private Set<Exercise> exercises = new HashSet<>();

    public Student(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
