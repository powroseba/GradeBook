package com.domain;


import com.entities.Exercises;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExerciseAndGrades {

    private Exercises exercise;

    private List<Integer> grades = new ArrayList<>();
}
