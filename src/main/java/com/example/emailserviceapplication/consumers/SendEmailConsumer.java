package com.example.emailserviceapplication.consumers;

import com.example.emailserviceapplication.events.SendEmail;
import com.example.emailserviceapplication.utils.EmailUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class SendEmailConsumer {
    // This class will consume messages from a message broker (e.g., RabbitMQ, Kafka)
    // and send emails using the EmailService.

    private ObjectMapper objectMapper;
    public SendEmailConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "send-email", groupId = "email-group")
    public void handlerForSendEmailTopic(String message) throws JsonProcessingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin@emailservice.com", "egerypjygvadmqki");
            }
        };
        Session session = Session.getInstance(props, auth);

        SendEmail sendEmail = objectMapper.readValue(
                message,
                SendEmail.class);


        EmailUtil.sendEmail(
                session,
                sendEmail.getTo(),
                sendEmail.getSubject(),
                sendEmail.getBody());
    }
}
