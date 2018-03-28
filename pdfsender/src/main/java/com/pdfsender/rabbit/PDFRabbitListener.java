package com.pdfsender.rabbit;

import com.domain.ExerciseAndGrades;
import com.domain.MailProperties;
import com.pdfsender.mail.EmailService;
import com.pdfsender.pdf.PDFGenerator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PDFRabbitListener {

    private PDFGenerator pdfGenerator;
    private EmailService emailService;

    @Autowired
    public PDFRabbitListener(PDFGenerator pdfGenerator, EmailService emailService) {
        this.pdfGenerator = pdfGenerator;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${jsa.rabbitmq.queue}")
    public void receivedMessage(ExerciseAndGrades exerciseAndGrades) {
        String mail = exerciseAndGrades.getEmail();
        byte[] pdf = pdfGenerator.createPDF(exerciseAndGrades);
        MailProperties mailProperties = new MailProperties(mail, "List of grades");
        mailProperties.setContent("\nThe list of grades is store in pdf.");
        emailService.sendMailWithAttachment(mailProperties, pdf);
    }

}
