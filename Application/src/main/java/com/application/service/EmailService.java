package com.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String myMail;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPlainText(String to, String title, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(title);
            message.setText(content);
            javaMailSender.send(message);
            LOGGER.info("Send email '{}' to: {}", title, to);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", to, e.getMessage()));
        }
    }
}
