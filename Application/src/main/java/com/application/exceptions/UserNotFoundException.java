package com.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User wosn't found")
public class UserNotFoundException extends RuntimeException {
}
