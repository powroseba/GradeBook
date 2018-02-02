package com.application.security;

import com.entities.UserModel;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService{

    private final UserModelRepository userModelRepository;

    @Autowired
    public AuthenticationService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userModelRepository.findByUsername(username);
        return (UserDetails) new User(userModel.getUsername(),
                userModel.getPassword(), userModel.getAuthorities());
    }
}
