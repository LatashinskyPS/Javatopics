package by.latashinsky.controllers;

import java.util.Locale;

public abstract class BaseShowAndCreateController<T> implements Controller {

    public boolean attemptToExecuteTheCommand(String s) {
        if (s == null) {
            return false;
        }
        switch (s.toLowerCase(Locale.ROOT)) {
            case "show" -> {
                show();
                return false;
            }
            case "exit" -> {
                return true;
            }
            case "create" -> {
                create();
                return false;
            }
            case "read" -> {
                read();
                return false;
            }
            case "help" -> {
                help();
                return false;
            }
            default -> {
                System.out.println("Unknown command! Try help.");
                return false;
            }
        }
    }

    @Override
    public void help() {
        System.out.println(
                """
                        show - вывести список сущностей
                        exit - перейти к предыдущему меню
                        create - создать новую сущность
                        read - вывести краткий список сущностей,выбрать\s
                        нужную для изучения и последующей настройки
                        help - вывести данное меню"""
        );
    }

    abstract void show();

    abstract void create();

    abstract void read();
}
