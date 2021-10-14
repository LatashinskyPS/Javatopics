package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.controllers.AccountController;

import java.util.Scanner;

public class AccountUI {
    static AccountController accountController = AccountController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run() {
        while (true) {
            System.out.print("/accounts>>");
            String command = in.next();
            if (accountController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}