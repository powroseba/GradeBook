package com.domain;


import com.entities.Exercises;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseAndGrades {

    private Exercises exercise;

    private List<Integer> grades;
}
