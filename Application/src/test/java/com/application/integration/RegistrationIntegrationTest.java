package com.application.integration;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.domain.AuthModel;
import com.entities.*;
import com.repositories.SchoolClassRepository;
import com.repositories.UserModelRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class
)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties"
)
public class RegistrationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserModelRepository userRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void seedData() {
        this.token = tokenGenerator.generate(100, "admin", UserRole.ADMIN);
    }

    @After
    public void cleanData() {
        userRepository.deleteAll();
    }

    @Test
    public void signUpExpectUserAlreadyExist() throws Exception {
        userRepository.save(new UserModel("known@mail.com","known","password",UserRole.TEACHER.name()));
        AuthModel authModel = new AuthModel("known@mail.com","name","surname",UserRole.TEACHER.name(), new Date());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isConflict())
                .andExpect(status().reason("User with that email or username already exist in our service!"));
    }

    @Test
    public void signUpExpectAuthModelFailValidation() throws Exception {
        AuthModel authModel = new AuthModel("ema@mail.com","name","surname",UserRole.TEACHER.name(), new Date());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Username size must be longer then 4, so the email address is to short!"));
    }

    @Test
    public void signUpTeacher() throws Exception {
        AuthModel authModel = new AuthModel("teacher@mail.com","name","surname",UserRole.TEACHER.name(), new Date());
        authModel.setExercise(Exercises.BIOLOGY.name());

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isCreated());
    }

    @Test
    public void signUpExpectSchoolClassNotFound() throws Exception {
        AuthModel authModel = new AuthModel("student@mail.com","name","surname",UserRole.STUDENT.name(), new Date());
        authModel.setSchoolClassName("unknown");

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("School class was't found!"));
    }

    @Test
    public void signUpStudent() throws Exception {
        schoolClassRepository.save(new SchoolClass("1A", Profile.ANGELIAN));
        AuthModel authModel = new AuthModel("student@mail.com","name","surname",UserRole.STUDENT.name(), new Date());
        authModel.setSchoolClassName("1A");

        mvc.perform(post("/auth").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(authModel)))
                .andExpect(status().isCreated());

    }
}
