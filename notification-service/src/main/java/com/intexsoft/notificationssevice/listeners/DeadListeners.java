package com.intexsoft.notificationssevice.listeners;

import com.intexsoft.notificationssevice.models.MessageFromBank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeadListeners {
    private static final Logger logger = LoggerFactory.getLogger(DeadListeners.class);

    @Value("${spring.rabbitmq.max-retries}")
    private int MAX_RETRIES;

    private final RabbitTemplate rabbitTemplate;

    public DeadListeners(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "dead-telegram")
    public void logTelegramError(MessageFromBank myMessage, Message message) {
        if (tryRetryMessage(message)) return;
        logger.warn("Can not send to telegram:" + myMessage.getAddress());
    }

    @RabbitListener(queues = "dead-email")
    public void logErrorEmail(MessageFromBank messageFromBank, Message message) {
        if (tryRetryMessage(message)) return;
        logger.warn("Can not send to email:" + messageFromBank.getAddress());
    }

    private boolean tryRetryMessage(Message message) {
        Object retries = message.getMessageProperties().getHeader("retries");
        if (retries == null) {
            message.getMessageProperties().setHeader("retries", 1);
            retries = 1;
        } else {
            message.getMessageProperties().setHeader("retries", (int) retries + 1);
            retries = (int) retries + 1;
        }
        if ((int) retries < MAX_RETRIES) {
            rabbitTemplate.setExchange("main-exchange");
            rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedRoutingKey(), message);
            return true;
        }
        return false;
    }
}
