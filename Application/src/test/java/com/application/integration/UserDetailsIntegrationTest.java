package com.application.integration;

import com.application.Application;
import com.application.ConvertJavaObjectToJson;
import com.application.TokenGenerator;
import com.domain.UserData;
import com.entities.UserModel;
import com.entities.UserRole;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class UserDetailsIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserModelRepository userRepository;

    private TokenGenerator tokenGenerator = new TokenGenerator();
    private String token = "";

    @Before
    public void seedData() {
        userRepository.save(new UserModel("email@mail.com","email","password", UserRole.STUDENT.name()));
        this.token = tokenGenerator.generate(100, "email", UserRole.STUDENT);
    }

    @After
    public void deleteData() {
        userRepository.deleteAll();
    }

    @Test
    public void changeEmailExpectOk() throws Exception {
        UserData userData = new UserData();
        userData.setEmail("newEmail@com.pl");

        mvc.perform(put("/user/email").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isOk());
    }

    @Test
    public void changeEmailExpectNotFound() throws Exception {
        this.token = tokenGenerator.generate(100, "unknownuser", UserRole.STUDENT);
        UserData userData = new UserData();
        userData.setEmail("newEmail@com.pl");

        mvc.perform(put("/user/email").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void changePasswordExpectOk() throws Exception {
        UserData userData = new UserData(null, null, "password", "newPassword", "newPassword");

        mvc.perform(put("/user/pass").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isOk());
    }

    @Test
    public void changePasswordExpectNotFound() throws Exception {
        this.token = tokenGenerator.generate(100, "unknownuser", UserRole.STUDENT);

        mvc.perform(put("/user/pass").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(new UserData())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void changePasswordExpectBadRequestDifferentNewPass() throws Exception {
        UserData userData = new UserData(null, null, "password", "newPassword", "otherPassword");

        mvc.perform(put("/user/pass").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("New passwords isn't the same!"));
    }

    @Test
    public void changePasswordExpectBadRequestDifferentCurrentPass() throws Exception {
        UserData userData = new UserData(null, null, "differentPass", "newPassword", "newPassword");

        mvc.perform(put("/user/pass").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(userData)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Current password is incorrect!"));
    }
}
