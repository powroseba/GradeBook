package com.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "STUDENT")
@Setter
@Getter
@NoArgsConstructor
public class Student extends UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "student", targetEntity = UserModel.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "USER_ID")
    private UserModel userModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "SCHOOL_CLASS_ID")
    private SchoolClass schoolClass;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private Set<Excercise> excercises;

    public Student(String firstName, String lastName, Date yearOfBirth, SchoolClass schoolClass, UserModel userModel) {
        super(firstName, lastName, yearOfBirth);
        this.schoolClass = schoolClass;
        this.userModel = userModel;
    }
}
