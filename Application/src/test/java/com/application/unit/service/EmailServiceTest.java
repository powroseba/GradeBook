package com.application.unit.service;

import com.application.service.implementations.EmailService;
import com.domain.MailProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void emailServiceSendPlainTextTest() throws Exception {
        MailProperties mailProperties = new MailProperties("to", "string", "content");

        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        emailService.sendPlainText(mailProperties);
    }
}
