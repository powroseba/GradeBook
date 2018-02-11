package com.application.unit.rest;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.application.exceptions.AuthModelValidationException;
import com.application.exceptions.SchoolClassNotFound;
import com.application.exceptions.UserAlreadyExistException;
import com.application.service.RegistrationServiceImpl;
import com.domain.AuthModel;
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

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegistrationServiceImpl registrationService;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void generateToken() {
        this.token = tokenGenerator.generate(10, "username", UserRole.ADMIN);
    }


    @Test
    public void signUp() throws Exception {
        AuthModel authModel = getAuthTestModel();

        ArgumentCaptor<AuthModel> arg = ArgumentCaptor.forClass(AuthModel.class);

        doNothing().when(registrationService).signUp(arg.capture());

        mvc.perform(post("/auth").header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isCreated());
        verify(registrationService, times(1)).signUp(arg.capture());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void signUpExpectBadRequest() throws Exception {
        AuthModel authModel = getAuthTestModel();

        ArgumentCaptor<AuthModel> arg = ArgumentCaptor.forClass(AuthModel.class);

        doThrow(new AuthModelValidationException("")).when(registrationService).signUp(arg.capture());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isBadRequest());
        verify(registrationService, times(1)).signUp(arg.capture());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void signUpExpectConflict() throws Exception {
        AuthModel authModel = getAuthTestModel();

        ArgumentCaptor<AuthModel> arg = ArgumentCaptor.forClass(AuthModel.class);

        doThrow( new UserAlreadyExistException()).when(registrationService).signUp(arg.capture());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isConflict());
        verify(registrationService, times(1)).signUp(arg.capture());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    public void signUpExpectNotFound() throws Exception {
        AuthModel authModel = getAuthTestModel();

        ArgumentCaptor<AuthModel> arg = ArgumentCaptor.forClass(AuthModel.class);

        doThrow( new SchoolClassNotFound("")).when(registrationService).signUp(arg.capture());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isNotFound());
        verify(registrationService, times(1)).signUp(arg.capture());
        verifyNoMoreInteractions(registrationService);
    }


    public AuthModel getAuthTestModel() {
       return new AuthModel("newmail@mail.com","Name","Surname",UserRole.ADMIN.name(), new Date());
    }


}
