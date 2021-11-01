package com.latashinsky.banksservice.interfaces;

import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.controllers.UserSettingsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserSettingsUi {
    static UserSettingsController userSettingsController = UserSettingsController.getInstance();
    static Scanner in = new Scanner(System.in).useDelimiter("\n");
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsUi.class);

    public static void run(User<Account<?, ?, ?, ?>> user) {
        while (true) {
            logger.info("/users/" + user.getId() + ">>");
            String command = in.next();
            if (userSettingsController.attemptToExecuteTheCommand(command, user)) {
                break;
            }
        }
    }
}
