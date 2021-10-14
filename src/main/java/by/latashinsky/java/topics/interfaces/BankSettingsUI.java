package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.controllers.BankSettingsController;
import by.latashinsky.java.topics.entities.Bank;

import java.util.Scanner;

public class BankSettingsUI {
    static BankSettingsController bankSettingsController = BankSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");

    public static void run(Bank bank) {
        while (true) {
            System.out.print("/banks/" + bank.getId() + ">>");
            String command = in.next();
            if (bankSettingsController.attemptToExecuteTheCommand(command, bank)) {
                break;
            }
        }
    }
}
