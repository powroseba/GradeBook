package com.application.service;


import com.application.exceptions.SchoolClassNotFound;
import com.domain.AuthModel;

public interface RegistrationService {
    void signUp(AuthModel authModel);
}
