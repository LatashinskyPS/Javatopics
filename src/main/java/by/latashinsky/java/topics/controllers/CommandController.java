package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.interfaces.AccountUI;
import by.latashinsky.java.topics.interfaces.BankUI;
import by.latashinsky.java.topics.interfaces.TransactionUI;
import by.latashinsky.java.topics.interfaces.UserUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class CommandController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);
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
            case "exit": {
                return true;
            }
            case "banks": {
                BankUI.run();
                return false;
            }
            case "users": {
                UserUI.run();
                return false;
            }
            case "help": {
                help();
                return false;
            }
            case "accounts": {
                AccountUI.run();
                return false;
            }
            case "transactions": {
                TransactionUI.run();
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
                "banks - перейти к меню управлению банков\n" +
                        "exit - закончить работу\n" +
                        "users - перейти к управлению пользователями\n" +
                        "accounts - перейти к управлению счетам\n" +
                        "transactions - перейти к управлению тразакциями\n" +
                        "help - вывести данное меню\n"
        );
    }
}
