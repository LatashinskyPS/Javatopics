package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.controllers.UserTransactionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserTransactionUi {
    static UserTransactionsController userTransactionsController = UserTransactionsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionUi.class);

    public static void run(User<Account<?, ?, ?, ?>> user) {
        while (true) {
            logger.info("/transactions/" + user.getId() + ">>");
            String command = in.next();
            if (userTransactionsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
