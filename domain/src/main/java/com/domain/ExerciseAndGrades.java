package com.domain;


import com.entities.Exercises;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ExerciseAndGrades {

    private HashMap<Exercises, ArrayList<Integer>> exerciseAndGradeList;

    private String email;

    public ExerciseAndGrades(String email) {
        this.exerciseAndGradeList = new HashMap<Exercises, ArrayList<Integer>>();
        this.email = email;
    }
}
