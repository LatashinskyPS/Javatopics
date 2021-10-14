package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.UserTransactionsController;
import by.latashinsky.java.topics.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserTransactionUI {
    static UserTransactionsController userTransactionsController = UserTransactionsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionUI.class);

    public static void run(User user) {
        while (true) {
            logger.info("/transactions/" + user.getId() + ">>");
            String command = in.next();
            if (userTransactionsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
