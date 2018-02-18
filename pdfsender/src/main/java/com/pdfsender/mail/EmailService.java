package com.pdfsender.mail;

import com.domain.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

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

    public void sendMailWithAttachment(MailProperties mailProperties, byte[] pdf) {
        try {
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(mailProperties.getContent());

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(pdf, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("test.pdf");

            //construct the mime multi part
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            mimeMultipart.addBodyPart(pdfBodyPart);

            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(myMail);
            InternetAddress iaRecipient = new InternetAddress(mailProperties.getTo());

            //construct the mime message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(mailProperties.getSubject());
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.setContent(mimeMultipart);

            javaMailSender.send(mimeMessage);
//            LOGGER.info("Send email '{}' to: {}", title, to);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", mailProperties.getTo(), e.getMessage()));
        }
    }
}
