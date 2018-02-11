package com.application.unit.rest;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.application.exceptions.DifferentNewPasswordsException;
import com.application.exceptions.UncorrectCurrentPasswordsException;
import com.application.exceptions.UserNotFoundException;
import com.application.service.UserDetailsServiceImpl;
import com.domain.UserData;
import com.entities.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class UserDetailsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void generateToken() {
        this.token = tokenGenerator.generate(10, "username", UserRole.STUDENT);
    }

    @Test
    public void changeEmail() throws Exception {
        UserData userData = new UserData();
        userData.setEmail("username@mail.com");
        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doNothing().when(userDetailsService).changeEmail(any(), arg.capture());
        mvc.perform(put("/user/email").header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isOk());
        verify(userDetailsService, times(1)).changeEmail(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    public void changeEmailExpectUserNotFound() throws Exception {
        UserData userData = new UserData();
        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doThrow(new UserNotFoundException()).when(userDetailsService).changeEmail(any(), arg.capture());

        mvc.perform(put("/user/email").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isNotFound());
        verify(userDetailsService, times(1)).changeEmail(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    public void changePassword() throws Exception {
        UserData userData = new UserData();
        userData.setCurrentPassword("passwordCurrent");
        userData.setNewPassword("passwordNew");
        userData.setNewRepeatPassword("passwordNew");

        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doNothing().when(userDetailsService).changeEmail(any(), arg.capture());

        mvc.perform(put("/user/pass").header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isOk());
        verify(userDetailsService, times(1)).changePassword(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    public void changePasswordExpectUserNotFound() throws Exception {
        UserData userData = new UserData();

        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doThrow(new UserNotFoundException()).when(userDetailsService).changePassword(any(), arg.capture());

        mvc.perform(put("/user/pass").header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isNotFound());
        verify(userDetailsService, times(1)).changePassword(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    public void changePasswordExpectBadRequestDifferentNewPasswords() throws Exception {
        UserData userData = new UserData();

        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doThrow(new DifferentNewPasswordsException()).when(userDetailsService).changePassword(any(), arg.capture());

        mvc.perform(put("/user/pass").header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("New passwords isn't the same!"));
        verify(userDetailsService, times(1)).changePassword(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }

    @Test
    public void changePasswordExpectBadRequestUncorrectCurrentPassword() throws Exception {
        UserData userData = new UserData();

        ArgumentCaptor<UserData> arg = ArgumentCaptor.forClass(UserData.class);

        doThrow(new UncorrectCurrentPasswordsException()).when(userDetailsService).changePassword(any(), arg.capture());

        mvc.perform(put("/user/pass").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Current password is incorrect!"));
        verify(userDetailsService, times(1)).changePassword(any(), arg.capture());
        verifyNoMoreInteractions(userDetailsService);
    }
}
