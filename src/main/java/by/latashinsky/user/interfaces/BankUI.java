package by.latashinsky.user.interfaces;

import by.latashinsky.controllers.BankController;

import java.util.Scanner;

public class BankUI {
    static BankController bankController = BankController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run() {
        while (true) {
            System.out.print("/banks>>");
            String command = in.next();
            if (bankController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
