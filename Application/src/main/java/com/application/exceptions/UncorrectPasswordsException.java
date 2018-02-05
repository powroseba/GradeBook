package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UncorrectPasswordsException extends RuntimeException {
    public UncorrectPasswordsException(String s) {
        super(s);
    }
}
