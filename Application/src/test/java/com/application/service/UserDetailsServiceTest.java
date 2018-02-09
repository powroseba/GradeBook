package com.application.service;

import com.domain.UserData;
import com.entities.UserModel;
import com.repositories.UserModelRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserModelRepository userModelRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void changeEmail() throws Exception {
        UserData userData = new UserData("email@mail.com", "email", "currentPassword",
                                        "newPassword", "newPassword");
        when(userModelRepository.findByUsername("")).thenReturn(new UserModel());
//        doNothing().when(userModelRepository).save(new UserModel());

        userDetailsService.changeEmail(any(HttpServletRequest.class), userData);
    }
}
