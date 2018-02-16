package com.application.exceptions.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Exercise wasn't found!")
public class ExerciseNotFoundException extends RuntimeException {
}
