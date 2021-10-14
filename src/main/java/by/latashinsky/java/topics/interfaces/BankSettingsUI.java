package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.BankSettingsController;
import by.latashinsky.java.topics.entities.Bank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankSettingsUI {
    static BankSettingsController bankSettingsController = BankSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(BankSettingsUI.class);

    public static void run(Bank bank) {
        while (true) {
            logger.info("/banks/" + bank.getId() + ">>");
            String command = in.next();
            if (bankSettingsController.attemptToExecuteTheCommand(command, bank)) {
                break;
            }
        }
    }
}
