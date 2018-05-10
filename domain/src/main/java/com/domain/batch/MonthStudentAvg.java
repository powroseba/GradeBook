package com.domain.batch;

import com.entities.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MonthStudentAvg {

    private String firstName;

    private String lastName;

    private String name;

    private Profile profile;

    private double gradesAverage;
}
