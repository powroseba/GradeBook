package com.application.service;

import com.application.exceptions.UncorrectPasswordsException;
import com.application.exceptions.UserNotFoundException;
import com.application.model.UserData;
import com.application.tools.TokenUsernameParserService;
import com.entities.UserModel;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private UserModelRepository userModelRepository;

    @Autowired
    public UserDetailsServiceImpl(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public void changeEmail(HttpServletRequest request, UserData userData) {
        String username = TokenUsernameParserService.parseUsername(request);
        UserModel userModel = userModelRepository.findByUsername(username);

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserNotFoundException();
        } else {
            userModel.setEmail(userData.getEmail());
            userModelRepository.save(userModel);
        }
    }

    @Override
    public void changePassword(HttpServletRequest request, UserData userData) {
        String username = TokenUsernameParserService.parseUsername(request);
        UserModel userModel = userModelRepository.findByUsername(username);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!Optional.ofNullable(userModel).isPresent()) {
            throw new UserNotFoundException();
        } else {

            if (!userData.getNewPassword().equals(userData.getNewRepeatPassword())) {
                throw new UncorrectPasswordsException("passwords isn't the same!");
            } else if (!encoder.matches(userData.getCurrentPassword(), userModel.getPassword())) {
                throw new UncorrectPasswordsException("current password is invalid!");
            }
            else {
                userModel.setPassword(userData.getNewPassword());
                userModelRepository.save(userModel);
            }
        }
    }
}
