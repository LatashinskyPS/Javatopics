package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.controllers.BankSettingsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankSettingsUi {
    static BankSettingsController bankSettingsController = BankSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(BankSettingsUi.class);

    public static void run(Bank<Account<?, ?, ?, ?>> bank) {
        while (true) {
            logger.info("/banks/" + bank.getId() + ">>");
            String command = in.next();
            if (bankSettingsController.attemptToExecuteTheCommand(command, bank)) {
                break;
            }
        }
    }
}
