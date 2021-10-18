package com.latashinsky.java.topics.interfaces;

import com.latashinsky.java.topics.controllers.AccountSettingsController;
import com.latashinsky.java.topics.entities.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsUi {
    static AccountSettingsController accountSettingsController = AccountSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsUi.class);

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
