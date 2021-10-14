package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.TransactionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class TransactionUI {
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
