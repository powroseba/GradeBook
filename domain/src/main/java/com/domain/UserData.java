package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

    @Email
    private String email;

    @Size(min = 4, max = 15)
    private String username;

    @Size(min = 8)
    private String currentPassword;

    @Size(min = 8)
    private String newPassword;

    @Size(min = 8)
    private String newRepeatPassword;
}
