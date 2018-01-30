package com.application;

import com.entities.UserModel;
import com.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DBSeeder {
    @Autowired
    private UserModelRepository userModelRepository;

    @PostConstruct
    public void seed(){
        UserModel userModel = new UserModel("powrseba@gmail.com", "powroseba", "password", "ADMIN");
        userModelRepository.save(userModel);

        System.out.println(userModelRepository.findByUsername("powroseba").getAuthorities());
    }
}
