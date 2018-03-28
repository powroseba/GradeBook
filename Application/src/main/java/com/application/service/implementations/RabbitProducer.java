package com.application.service.implementations;

import com.application.service.GradeService;
import com.domain.ExerciseAndGrades;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class RabbitProducer {

    private GradeService gradeService;

    private AmqpTemplate amqpTemplate;

    @Value("${jsa.rabbitmq.exchange}")
    private String exchange;

    @Value("${jsa.rabbitmq.routingkey}")
    private String routingKey;


    @Autowired
    public RabbitProducer(GradeService gradeService, AmqpTemplate amqpTemplate) {
        this.gradeService = gradeService;
        this.amqpTemplate = amqpTemplate;
    }

    public void sendGrades(HttpServletRequest request) {
        ExerciseAndGrades exerciseAndGrades = gradeService.findGradesForStudent(request);
        amqpTemplate.convertAndSend(exchange, routingKey, exerciseAndGrades);
    }

    /**
     * This post construct is use to auto generate queue, exchange and key in rabbitMQ
     */
    @PostConstruct
    public void generateConfig() {
        amqpTemplate.convertAndSend(exchange, routingKey);
    }
}
