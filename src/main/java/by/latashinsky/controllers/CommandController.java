package by.latashinsky.controllers;

import by.latashinsky.user.interfaces.AccountUI;
import by.latashinsky.user.interfaces.BankUI;
import by.latashinsky.user.interfaces.TransactionUI;
import by.latashinsky.user.interfaces.UserUI;

import java.util.Locale;

public class CommandController implements Controller {
    private static CommandController commandController;

    private CommandController() {
    }

    public static CommandController getInstance() {
        if (commandController == null) {
            commandController = new CommandController();
        }
        return commandController;
    }

    public boolean attemptToExecuteTheCommand(String s) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit" -> {
                return true;
            }
            case "banks" -> {
                BankUI.run();
                return false;
            }
            case "users" -> {
                UserUI.run();
                return false;
            }
            case "help" -> {
                help();
                return false;
            }
            case "accounts" -> {
                AccountUI.run();
                return false;
            }
            case "transactions"->{
                TransactionUI.run();
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
                        banks - перейти к меню управлению банков
                        exit - закончить работу
                        users - перейти к управлению пользователями
                        accounts - перейти к управлению счетам
                        transactions - перейти к управлению тразакциями
                        help - вывести данное меню"""
        );
    }
}
