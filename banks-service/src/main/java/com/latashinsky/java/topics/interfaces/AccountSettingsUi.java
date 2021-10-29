package com.latashinsky.java.topics.interfaces;

import com.latashinsky.java.topics.controllers.AccountSettingsController;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsUi {
    static AccountSettingsController accountSettingsController = AccountSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsUi.class);

    public static void run(Account<?, Currency<?>, Bank<?>, User<?>> account) {
        while (true) {
            logger.info("/banks/" + account.getId() + ">>");
            String command = in.next();
            if (accountSettingsController.attemptToExecuteTheCommand(command, account)) {
                break;
            }
        }
    }

}
