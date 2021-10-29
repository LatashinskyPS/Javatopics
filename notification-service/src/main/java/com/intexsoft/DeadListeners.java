package com.intexsoft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadListeners {
    private static final Logger logger = LoggerFactory.getLogger(DeadListeners.class);

    @RabbitListener(queues = "dead-telegram")
    public void logTelegramError(String json) {
        MyMessage myMessage;
        try {
            myMessage = new ObjectMapper().readValue(json, MyMessage.class);
        } catch (JsonProcessingException e) {
            logger.warn("Can not parse:\n" + json);
            return;
        }
        logger.warn("Can not send to telegram:" + myMessage.getAddress());
    }

    @RabbitListener(queues = "dead-email")
    public void logErrorError(String json) {
        MyMessage myMessage;
        try {
            myMessage = new ObjectMapper().readValue(json, MyMessage.class);
        } catch (JsonProcessingException e) {
            logger.warn("Can not parse:\n" + json);
            return;
        }
        logger.warn("Can not send to email:" + myMessage.getAddress());
    }
}
