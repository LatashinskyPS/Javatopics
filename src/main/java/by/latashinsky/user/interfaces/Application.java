package by.latashinsky.user.interfaces;

import by.latashinsky.controllers.CommandController;

import java.util.Scanner;

public class Application {
    static CommandController commandController = CommandController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run() {
        while (true) {
            System.out.print(">>");
            String command = in.next();
            if (commandController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
        System.out.print(">> And of work.");
    }
}
