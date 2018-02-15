package com.domain.dto;

import com.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SchoolClassDTO {

    private Long id;

    private Teacher tutor;

    private String name;

    private Profile profile;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Exercise> exercises = new HashSet<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Student> students = new HashSet<>();

    public SchoolClassDTO(Long id, Teacher tutor, String name, Profile profile, Set<Exercise> exercises, Set<Student> students) {
        this.id = id;
        this.tutor = tutor;
        this.name = name;
        this.profile = profile;
        this.exercises = exercises;
        this.students = students;
    }

    public static SchoolClassDTO convert(SchoolClass schoolClass) {
        return new SchoolClassDTO(schoolClass.getId(), schoolClass.getTutor(), schoolClass.getName(),
                                schoolClass.getProfile(), schoolClass.getExercises(), schoolClass.getStudents());
    }

    public static SchoolClass convert(SchoolClassDTO schoolClassDTO) {
        SchoolClass schoolClass = new SchoolClass(schoolClassDTO.getName(), schoolClassDTO.getProfile());
        schoolClass.setTutor(schoolClassDTO.getTutor());
        schoolClass.setExercises(schoolClassDTO.getExercises());
        schoolClass.setStudents(schoolClassDTO.getStudents());
        return schoolClass;
    }
}
