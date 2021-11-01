package com.latashinsky.banksservice.services;


import com.latashinsky.banksservice.helpers.MessageFromBank;
import com.latashinsky.banksservice.entities.User;
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
                MessageFromBank messageFromBank = new MessageFromBank();
                messageFromBank.setTheme(mapping);
                messageFromBank.setAddress(email);
                messageFromBank.setMessage(message);
                rabbitTemplate.setExchange("main-exchange");
                rabbitTemplate.convertAndSend(mapping + ".email", messageFromBank);
                if (user.getChatId() != null) {
                    messageFromBank.setAddress(user.getChatId());
                    rabbitTemplate.convertAndSend(mapping + ".telegram", messageFromBank);
                }
            }
        }
    }
}
