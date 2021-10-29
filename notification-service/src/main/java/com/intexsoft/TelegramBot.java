package com.intexsoft;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.token}")
    private String token;

    public void sendMessage(MyMessage myMessage) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(myMessage.getAddress());
        sendMessage.setText(myMessage.getMessage());
        execute(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return "Java-Topics";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getChatId().toString());
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
