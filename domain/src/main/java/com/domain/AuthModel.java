package com.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AuthModel {

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3)
    private String lastName;

    @NotNull
    private String role;

    @NotNull
    private String schoolClassName;

    private String excercise;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date yearOfBirth;

    public AuthModel(String email, String role, String schoolClassName, Date yearOfBirth) {
        this.email = email;
        this.role = role;
        this.schoolClassName = schoolClassName;
        this.yearOfBirth = yearOfBirth;
    }
}
