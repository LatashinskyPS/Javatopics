package com.latashinsky.java.topics.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public abstract class BaseShowAndCreateController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(BaseShowAndCreateController.class);

    public boolean attemptToExecuteTheCommand(String s) {
        if (s == null) {
            return false;
        }
        switch (s.toLowerCase(Locale.ROOT)) {
            case "show": {
                show();
                return false;
            }
            case "exit": {
                return true;
            }
            case "create": {
                create();
                return false;
            }
            case "read": {
                read();
                return false;
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

    @Override
    public void help() {
        logger.info(
                "show - вывести список сущностей\n"
                        + "exit - перейти к предыдущему меню \n"
                        + "create - создать новую сущность \n"
                        + "read - вывести краткий список сущностей,выбрать\n"
                        + "нужную для изучения и последующей настройки\n"
                        + "help - вывести данное меню\n"
        );
    }

    abstract void show();

    abstract void create();

    abstract void read();
}
