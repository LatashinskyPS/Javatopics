package by.latashinsky.user.interfaces;

import by.latashinsky.controllers.UserTransactionsController;
import by.latashinsky.entities.User;

import java.util.Scanner;

public class UserTransactionUI {
    static UserTransactionsController userTransactionsController = UserTransactionsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run(User user) {
        while (true) {
            System.out.print("/transactions/" + user.getId() + ">>");
            String command = in.next();
            if (userTransactionsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
