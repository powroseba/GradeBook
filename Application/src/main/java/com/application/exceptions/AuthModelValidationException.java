package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AuthModelValidationException extends RuntimeException {
    public AuthModelValidationException(String s) {
        super(s);
    }
}
