package com.intexsoft.notificationssevice.listeners;

import com.intexsoft.notificationssevice.models.MessageFromBank;
import com.intexsoft.notificationssevice.bots.TelegramBot;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Listeners {
    private final JavaMailSender javaMailSender;
    private final TelegramBot telegramBot;
    @Value("${spring.mail.username}")
    private String email;

    public Listeners(JavaMailSender javaMailSender, TelegramBot telegramBot) {
        this.javaMailSender = javaMailSender;
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = "email")
    public void notifyEmail(MessageFromBank messageFromBank) {
        SimpleMailMessage mimeMailMessage = new SimpleMailMessage();
        mimeMailMessage.setFrom(email);
        mimeMailMessage.setTo(messageFromBank.getAddress());
        mimeMailMessage.setSubject(messageFromBank.getTheme());
        mimeMailMessage.setText(messageFromBank.getMessage());
        javaMailSender.send(mimeMailMessage);
    }

    @RabbitListener(queues = "telegram")
    public void notifyTelegram(MessageFromBank myMessage) throws TelegramApiException {
        telegramBot.sendMessage(myMessage);
    }
}
