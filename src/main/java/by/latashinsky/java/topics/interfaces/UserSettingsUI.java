package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.controllers.UserSettingsController;
import by.latashinsky.java.topics.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserSettingsUI {
    static UserSettingsController userSettingsController = UserSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsUI.class);

    public static void run(User user) {
        while (true) {
            logger.info("/users/" + user.getId() + ">>");
            String command = in.next();
            if (userSettingsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
