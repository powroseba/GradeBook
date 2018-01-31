package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull
    private Date yearOfBirth;

    @OneToOne(mappedBy = "teacher", targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @OneToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @OneToOne(fetch = FetchType.EAGER)
    private Exercise exercise;

    public Teacher(String firstName, String lastName, Date yearOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
    }
}
