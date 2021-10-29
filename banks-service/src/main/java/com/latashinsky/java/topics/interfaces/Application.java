package com.latashinsky.java.topics.interfaces;

import com.latashinsky.java.topics.controllers.CommandController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Application {
    static CommandController commandController = CommandController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void run() {
        while (true) {
            logger.info(">>");
            String command = in.next();
            if (commandController.attemptToExecuteTheCommand(command)) {
                break;
            }
        }
        System.out.print(">> And of work.");
    }
}
