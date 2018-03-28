package com.application.service.implementations;

import com.domain.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
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

    public void sendPlainText(MailProperties mailProperties) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(mailProperties.getTo());
            message.setSubject(mailProperties.getSubject());
            message.setText(mailProperties.getContent());
            javaMailSender.send(message);
            LOGGER.info("Send email '{}' to: {}", mailProperties.getSubject(), mailProperties.getTo());
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}",
                    mailProperties.getTo(), e.getMessage()));
        }
    }
}
