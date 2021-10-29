package com.latashinsky.java.topics.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public abstract class BaseSettingsController<T> implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(BaseSettingsController.class);

    public boolean attemptToExecuteTheCommand(String s, T t) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit": {
                return true;
            }
            case "show": {
                show(t);
                return false;
            }
            case "update": {
                update(t);
                return false;
            }
            case "delete": {
                return delete(t);
            }
            case "help": {
                help();
                return false;
            }
            default: {
                logger.info("Unknown command! Try help.\n");
                return false;
            }
        }
    }

    public abstract void show(T t);

    public abstract void update(T t);

    public abstract boolean delete(T t);

    @Override
    public void help() {
        logger.info(
                "show - вывести информацию о сущности"
                        + "exit - перейти к предыдущему меню \n"
                        + "delete - удалить данную сущность \n"
                        + "update - редактирование данной сущности \n"
                        + "help - вывести данное меню\n"
        );
    }
}
