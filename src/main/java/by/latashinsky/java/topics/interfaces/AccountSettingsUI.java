package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.controllers.AccountSettingsController;
import by.latashinsky.java.topics.entities.Account;

import java.util.Scanner;

public class AccountSettingsUI {
    static AccountSettingsController accountSettingsController = AccountSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run(Account account) {
        while (true) {
            System.out.print("/banks/" + account.getId() + ">>");
            String command = in.next();
            if (accountSettingsController.attemptToExecuteTheCommand(command, account)) {
                break;
            }
        }
    }

}
