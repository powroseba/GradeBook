package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username size must be longer then 4, so the email address is to short!")
public class AuthModelUsernameValidationException extends RuntimeException {
}
