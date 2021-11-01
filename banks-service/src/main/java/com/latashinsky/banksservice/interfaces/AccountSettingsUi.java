package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.controllers.AccountSettingsController;
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
