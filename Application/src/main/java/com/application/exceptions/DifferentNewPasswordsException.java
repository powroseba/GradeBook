package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "New passwords isn't the same!")
public class DifferentNewPasswordsException extends RuntimeException{
}
