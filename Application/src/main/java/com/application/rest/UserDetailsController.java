package com.application.rest;

import com.application.model.UserData;
import com.application.service.UserDetailsService;
import com.application.tools.TokenUsernameParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserDetailsController {

//    zmiana email zmiana hasla

    private UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/email")
    public void changeEmail(HttpServletRequest request, @RequestBody @Valid UserData userData) {
        userDetailsService.changeEmail(request, userData);
    }
}
