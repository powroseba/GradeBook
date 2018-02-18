package com.pdfsender;

import com.domain.ExerciseAndGrades;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PDFRabbitListener {

    @RabbitListener(queues = "${jsa.rabbitmq.queue}")
    public void receivedMessage(ExerciseAndGrades exerciseAndGrades) {
        System.out.println("Message: " + exerciseAndGrades);
    }

}
