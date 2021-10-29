package com.latashinsky.java.topics.interfaces;

import com.latashinsky.java.topics.MainClass;
import com.latashinsky.java.topics.controllers.TransactionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class TransactionUi {
    static TransactionController transactionController = TransactionController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(MainClass.class);

    public static void run() {
        while (true) {
            logger.info("/transactions>>");
            String command = in.next();
            if (transactionController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
