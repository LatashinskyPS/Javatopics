package com.latashinsky.java.topics.interfaces;

import com.latashinsky.java.topics.controllers.UserTransactionsController;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
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
