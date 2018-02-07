package com.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String myMail;

    @Autowired
    public RegistrationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void signUp() throws MailException, MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("puvac@send22u.info");
        message.setSubject("test");
        message.setText("test");
        javaMailSender.send(message);

    }
}
