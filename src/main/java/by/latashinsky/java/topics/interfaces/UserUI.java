package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserUI {
    static UserController userController = UserController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(UserUI.class);

    public static void run() {
        while (true) {
            logger.info("/users>>");
            String command = in.next();
            if (userController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
