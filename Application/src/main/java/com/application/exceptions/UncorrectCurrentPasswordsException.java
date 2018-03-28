package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Current password is incorrect!")
public class UncorrectCurrentPasswordsException extends RuntimeException {
}
