package com.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

    private String schoolClassName;

    private String exercise;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    public AuthModel(String email, String firstName, String lastName, String role, Date dateOfBirth) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }
}
