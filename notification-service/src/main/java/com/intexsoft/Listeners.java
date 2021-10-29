package com.intexsoft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Listeners {
    private final JavaMailSender javaMailSender;
    private final TelegramBot telegramBot;

    public Listeners(JavaMailSender javaMailSender, TelegramBot telegramBot) {
        this.javaMailSender = javaMailSender;
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = "email")
    public void notifyEmail(String json) throws JsonProcessingException {
        MyMessage myMessage = new ObjectMapper().readValue(json, MyMessage.class);
        SimpleMailMessage mimeMailMessage = new SimpleMailMessage();
        mimeMailMessage.setFrom("pavel.lotashinski@intexsoft.by");
        mimeMailMessage.setTo(myMessage.getAddress());
        mimeMailMessage.setSubject("Info");
        mimeMailMessage.setText(myMessage.getMessage());
        javaMailSender.send(mimeMailMessage);
    }

    @RabbitListener(queues = "telegram")
    public void notifyTelegram(String json) throws JsonProcessingException, TelegramApiException {
        MyMessage myMessage = new ObjectMapper().readValue(json, MyMessage.class);
        telegramBot.sendMessage(myMessage);
    }
}
