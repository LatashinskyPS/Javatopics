package by.latashinsky.controllers;

import java.util.Locale;

public abstract class BaseSettingsController<T> implements Controller {

    public boolean attemptToExecuteTheCommand(String s, T t) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit" -> {
                return true;
            }
            case "show" -> {
                System.out.println(t);
                return false;
            }
            case "update" -> {
                update(t);
                return false;
            }
            case "delete" -> {
                return delete(t);
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

    public abstract void update(T t);

    public abstract boolean delete(T t);

    @Override
    public void help() {
        System.out.println(
                """
                        show - вывести информацию о сущности
                        exit - перейти к предыдущему меню
                        delete - удалить данную сущность
                        update - редактирование данной сущности
                        help - вывести данное меню"""
        );
    }
}
