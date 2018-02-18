package com.application.unit.service;

import com.application.TokenGenerator;
import com.application.exceptions.DifferentNewPasswordsException;
import com.application.exceptions.UncorrectCurrentPasswordsException;
import com.application.exceptions.notfound.UserNotFoundException;
import com.application.service.implementations.UserDetailsServiceImpl;
import com.domain.UserData;
import com.entities.UserModel;
import com.entities.UserRole;
import com.repositories.UserModelRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceTest {

    @Mock
    private UserModelRepository userModelRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private TokenGenerator tokenGenerator;
    private MockHttpServletRequest request;
    private UserData userData;

    @Before
    public void before() {
        this.tokenGenerator = new TokenGenerator();
        this.request = new MockHttpServletRequest();
        this.userData = new UserData("email@mail.com", "email", "currentPassword",
                "newPassword", "newPassword");
        this.request.addHeader("Authorization", tokenGenerator.generate(1000, userData.getUsername(),UserRole.STUDENT));
    }

    @Test
    public void changeEmail() throws Exception {
        UserModel userModel = new UserModel();
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(userModel);

        userDetailsService.changeEmail(request, userData);

        assertNotNull(userModel);
        assertNotNull(userData);
        assertNotNull(request);

        assertNotNull(userModelRepository.findByUsername(userData.getUsername()));

        assertThat(userData.getEmail()).isEqualTo(userModel.getEmail());
    }

    @Test(expected = UserNotFoundException.class)
    public void changeEmailExpectUserNotFoundException() throws Exception {
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(null);

        userDetailsService.changeEmail(request, userData);

        assertNull(userModelRepository.findByUsername(userData.getUsername()));
    }

    @Test
    public void changePassword() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserModel userModel = new UserModel("email@mail.com","email","currentPassword", UserRole.STUDENT.name());
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(userModel);

        assertNotNull(userModel);
        assertNotNull(userData);
        assertNotNull(request);

        assertNotNull(userModelRepository.findByUsername(userData.getUsername()));

        assertThat(encoder.matches(userData.getCurrentPassword(), userModel.getPassword())).isTrue();
        userDetailsService.changePassword(request, userData);
        assertThat(userData.getNewPassword()).isEqualTo(userData.getNewRepeatPassword());
        assertThat(encoder.matches(userData.getNewPassword(), userModel.getPassword())).isTrue();
    }

    @Test(expected = UserNotFoundException.class)
    public void changePasswordExpectUserNotFoundException() throws Exception {
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(null);

        userDetailsService.changePassword(request, userData);

        assertNull(userModelRepository.findByUsername(userData.getUsername()));
    }

    @Test
    public void changePasswordExpectDifferentNewPasswords() throws Exception {
        expectedEx.expect(DifferentNewPasswordsException.class);

        userData.setNewRepeatPassword(userData.getNewPassword() + "ups");
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(new UserModel());

        userDetailsService.changePassword(request, userData);

        assertNotNull(userData);
        assertNotNull(request);

        assertNotNull(userModelRepository.findByUsername(userData.getUsername()));

        assertThat(userData.getNewPassword()).isNotEqualTo(userData.getNewRepeatPassword());
    }

    @Test
    public void changePasswordExpectUncorrectedCurrentPasswords() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        expectedEx.expect(UncorrectCurrentPasswordsException.class);

        UserModel userModel = new UserModel("email@mail.com","email","differentPassword", UserRole.STUDENT.name());
        when(userModelRepository.findByUsername(userData.getUsername())).thenReturn(userModel);

        userDetailsService.changePassword(request, userData);

        assertNotNull(userData);
        assertNotNull(userModel);
        assertNotNull(request);

        assertNotNull(userModelRepository.findByUsername(userData.getUsername()));
        assertThat(userModelRepository.findByUsername(userData.getUsername())).isEqualTo(userModel);

        assertThat(encoder.matches(userData.getCurrentPassword(), userModel.getPassword())).isFalse();
    }
}
