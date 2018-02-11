package com.application.rest;

import com.application.service.UserDetailsService;
import com.domain.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserDetailsController {

    private UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PutMapping(value = "/email")
    public void changeEmail(HttpServletRequest request, @RequestBody @Valid UserData userData) {
        userDetailsService.changeEmail(request, userData);
    }

    @PutMapping(value = "/pass")
    public void changePassword(HttpServletRequest request, @RequestBody @Valid UserData userData) {
        userDetailsService.changePassword(request, userData);
    }
}
