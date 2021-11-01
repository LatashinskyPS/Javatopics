package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.controllers.BankController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankUi {
    static BankController bankController = BankController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(BankUi.class);

    public static void run() {
        while (true) {
            logger.info("/banks>>");
            String command = in.next();
            if (bankController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
