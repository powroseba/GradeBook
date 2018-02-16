package com.domain.dto;

import com.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseDTO {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Exercises nameOfExercise;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Grade> grades = new HashSet<>();

    private SchoolClass schoolClass;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Student> students = new HashSet<>();

    public ExerciseDTO(Long id, Teacher teacher, Exercises nameOfExercise, Set<Grade> grades,
                       SchoolClass schoolClass, Set<Student> students) {
        this.id = id;
        this.teacher = teacher;
        this.nameOfExercise = nameOfExercise;
        this.grades = grades;
        this.schoolClass = schoolClass;
        this.students = students;
    }

    public static ExerciseDTO convert(Exercise exercise) {
        return new ExerciseDTO(exercise.getId(), exercise.getTeacher(),
                exercise.getNameOfExercise(), exercise.getGrades(), exercise.getSchoolClass(),
                exercise.getStudents());
    }

    public static Exercise convert(ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setTeacher(exerciseDTO.getTeacher());
        exercise.setNameOfExercise(exerciseDTO.getNameOfExercise());
        exercise.setGrades(exerciseDTO.getGrades());
        exercise.setSchoolClass(exerciseDTO.getSchoolClass());
        exercise.setStudents(exerciseDTO.getStudents());
        return exercise;
    }
}
