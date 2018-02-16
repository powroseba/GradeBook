package com.domain.dto;

import com.entities.Exercise;
import com.entities.SchoolClass;
import com.entities.Teacher;
import com.entities.UserModel;
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
public class TeacherDTO {

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

    private SchoolClass schoolClass;

    private Set<Exercise> exercises = new HashSet<>();

    public TeacherDTO(Long id, String firstName, String lastName, Date dateOfBirth,
                      UserModel userModel, SchoolClass schoolClass, Set<Exercise> exercises) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.userModel = userModel;
        this.schoolClass = schoolClass;
        this.exercises = exercises;
    }

    public static TeacherDTO convert(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(), teacher.getLastName(),
                teacher.getDateOfBirth(), teacher.getUserModel(), teacher.getSchoolClass(),
                teacher.getExercises());
    }

    public static Teacher convert(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher(teacherDTO.getFirstName(), teacherDTO.getLastName(), teacherDTO.getDateOfBirth());
        teacher.setUserModel(teacherDTO.getUserModel());
        teacher.setSchoolClass(teacherDTO.getSchoolClass());
        teacher.setExercises(teacherDTO.getExercises());
        return teacher;
    }
}
