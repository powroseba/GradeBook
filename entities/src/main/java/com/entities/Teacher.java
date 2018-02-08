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
@Table(name = "TEACHER")
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends UserModelDetails {

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

    @OneToOne(mappedBy = "teacher", targetEntity = UserModel.class)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @OneToOne
    private SchoolClass schoolClass;

    @OneToMany(targetEntity = Exercise.class, fetch = FetchType.EAGER, mappedBy = "teacher")
    private Set<Exercise> exercises = new HashSet<>();;

    public Teacher(String firstName, String lastName, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
