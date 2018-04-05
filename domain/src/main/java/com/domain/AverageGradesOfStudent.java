package com.domain;

import com.entities.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AverageGradesOfStudent {

    Long studentId;

    String firstName;

    String lastName;

    String schoolClassName;

    Profile schoolClassProfile;

    Float averageGrades;
}
