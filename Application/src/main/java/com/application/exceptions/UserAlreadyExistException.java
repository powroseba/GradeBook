package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "User with that email or username already exist in our service!")
public class UserAlreadyExistException extends RuntimeException {
}
