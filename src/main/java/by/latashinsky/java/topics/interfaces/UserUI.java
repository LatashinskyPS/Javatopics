package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.controllers.UserController;

import java.util.Scanner;

public class UserUI {
    static UserController userController = UserController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run() {
        while (true) {
            System.out.print("/users>>");
            String command = in.next();
            if (userController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
