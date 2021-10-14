package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.AccountSettingsController;
import by.latashinsky.java.topics.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsUI {
    static AccountSettingsController accountSettingsController = AccountSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsUI.class);

    public static void run(Account account) {
        while (true) {
            logger.info("/banks/" + account.getId() + ">>");
            String command = in.next();
            if (accountSettingsController.attemptToExecuteTheCommand(command, account)) {
                break;
            }
        }
    }

}
