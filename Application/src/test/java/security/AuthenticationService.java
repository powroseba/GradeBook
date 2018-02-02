package security;

import com.application.Application;
import com.application.security.AccountCredentials;
import com.application.security.JWTLoginFilter;
import com.entities.UserModel;
import com.repositories.UserModelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class)
public class AuthenticationService {

    @Autowired
    private MockMvc mvc;

    @Mock
    private JWTLoginFilter jwtLoginFilter;

    @Mock
    private UserModelRepository userModelRepository;

    @Test
    public void logInNotFoundUserTest() throws Exception {
        when(userModelRepository.findByUsername("username")).thenReturn(null);


        AccountCredentials ac = new AccountCredentials();
        ac.setUsername("username");
        ac.setPassword("password");

        mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(ConvertJavaObjectToJson.asJsonString(ac)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void logInUnauthorizedUserTest() throws Exception {

    }
}
