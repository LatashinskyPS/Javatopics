package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.controllers.AccountController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountUi {
    static AccountController accountController = AccountController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(AccountUi.class);

    public static void run() {
        while (true) {
            logger.info("/accounts>>");
            String command = in.next();
            if (accountController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}