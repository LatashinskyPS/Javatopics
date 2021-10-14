package by.latashinsky.java.topics.interfaces;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.controllers.BankController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankUI {
    static BankController bankController = BankController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(BankUI.class);

    public static void run() {
        while (true) {
            logger.info("/banks>>");
            String command = in.next();
            if (bankController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
    }
}
