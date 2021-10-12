package by.latashinsky.user.interfaces;

import by.latashinsky.controllers.UserSettingsController;
import by.latashinsky.entities.User;

import java.util.Scanner;

public class UserSettingsUI {
    static UserSettingsController userSettingsController = UserSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run(User user) {
        while (true) {
            System.out.print("/banks/" + user.getId() + ">>");
            String command = in.next();
            if (userSettingsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
