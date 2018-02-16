package com.repositories;

import com.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByTeacher(Teacher teacher);

    Exercise findByTeacherAndSchoolClassAndNameOfExerciseAndStudentsIsContaining(Teacher teacher, SchoolClass schoolClass,
                                                                                 Exercises nameOfExercise, Student student);
}
