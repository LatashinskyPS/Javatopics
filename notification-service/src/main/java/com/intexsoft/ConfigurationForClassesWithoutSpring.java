package com.intexsoft;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;

@Component
public class ConfigurationForClassesWithoutSpring implements ApplicationContextAware {
    ApplicationContext applicationContext;
    private final TelegramBot telegramBot;

    public ConfigurationForClassesWithoutSpring(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void postConstruct() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBot);
    }
}
