package com.application.exceptions.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Teacher wasn't found!")
public class TeacherNotFoundException extends RuntimeException {
}
