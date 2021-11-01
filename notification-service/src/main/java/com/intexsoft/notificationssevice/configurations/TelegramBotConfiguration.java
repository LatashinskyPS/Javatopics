package com.intexsoft.notificationssevice.configurations;

import com.intexsoft.notificationssevice.bots.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotConfiguration {
    private final TelegramBot telegramBot;

    public TelegramBotConfiguration(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
        return botsApi;
    }
}
