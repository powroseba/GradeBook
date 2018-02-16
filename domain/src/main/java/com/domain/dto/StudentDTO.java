package com.domain.dto;

import com.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {

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

    private UserModel userModel;

    private Set<Grade> grades = new HashSet<>();

    private SchoolClass schoolClass;

    private Set<Exercise> exercises = new HashSet<>();

    public StudentDTO(Long id, String firstName, String lastName, Date dateOfBirth, UserModel
            userModel, Set<Grade> grades, SchoolClass schoolClass, Set<Exercise> exercises) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userModel = userModel;
        this.grades = grades;
        this.schoolClass = schoolClass;
        this.exercises = exercises;
    }

    public static StudentDTO convert(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(),
                student.getDateOfBirth(), student.getUserModel(), student.getGrades(),
                student.getSchoolClass(), student.getExercises());
    }

    public static Student convert(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getFirstName(), studentDTO.getLastName(), studentDTO.getDateOfBirth());
        student.setUserModel(studentDTO.getUserModel());
        student.setSchoolClass(studentDTO.getSchoolClass());
        student.setExercises(studentDTO.getExercises());
        student.setGrades(studentDTO.getGrades());
        return student;
    }
}
