package com.application.service;

import com.domain.UserData;

import javax.servlet.http.HttpServletRequest;

public interface UserDetailsService {

    void changeEmail(HttpServletRequest request, UserData userData);

    void changePassword(HttpServletRequest request, UserData userData);
}
