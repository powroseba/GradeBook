package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TEACHER")
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "teacher", targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @OneToOne(fetch = FetchType.LAZY)
    private SchoolClass schoolClass;

    @OneToOne(fetch = FetchType.EAGER)
    private Excercise excercise;

    public Teacher(String firstName, String lastName, Date yearOfBirth, SchoolClass schoolClass, UserModel userModel) {
        super(firstName, lastName, yearOfBirth);
        this.schoolClass = schoolClass;
        this.userModel = userModel;
    }
}
