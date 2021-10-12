package by.latashinsky.user.interfaces;

import by.latashinsky.controllers.TransactionController;

import java.util.Scanner;

public class TransactionUI {
    static TransactionController transactionController = TransactionController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run() {
        while (true) {
            System.out.print("/transactions>>");
            String command = in.next();
            if (transactionController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
