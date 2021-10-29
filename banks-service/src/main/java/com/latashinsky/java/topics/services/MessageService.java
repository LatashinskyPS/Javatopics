package com.latashinsky.java.topics.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.controllers.AccountController;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.helpers.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public MessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(User<?> user, String message, String mapping) {
        if (user.isEnabledNotifications()) {
            if (user.getEmail() != null) {
                String email = user.getEmail();
                MyMessage myMessage = new MyMessage();
                myMessage.setAddress(email);
                myMessage.setMessage(message);
                rabbitTemplate.setExchange("main-exchange");
                try {
                    rabbitTemplate.convertAndSend(mapping + ".email", new ObjectMapper().writeValueAsString(myMessage));
                } catch (JsonProcessingException e) {
                    logger.info(e.getMessage());
                }
                if (user.getChatId() != null) {
                    myMessage.setAddress(user.getChatId());
                    try {
                        rabbitTemplate.convertAndSend(mapping + ".telegram", new ObjectMapper().writeValueAsString(myMessage));
                    } catch (JsonProcessingException e) {
                        logger.info(e.getMessage());
                    }
                }
            }
        }
    }
}
