package com.latashinsky.java.topics.services;

import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.UserTypes;
import com.latashinsky.java.topics.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final UserService instance = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return instance;
    }

    public void editName(User<?> user) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter user name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                user.setName(str);
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    public void editUserType(User<?> user) {
        logger.info("Types:\n1)Legal\n2)Usual\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter number of type:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                if (index == 1) {
                    user.setUserType(UserTypes.LEGAL);
                    return;
                }
                if (index == 2) {
                    user.setUserType(UserTypes.USUAL);
                    return;
                }
            }
            logger.info("Invalid input!\n");
        }
    }

    public void editEmail(User<?> user) {
        logger.info("Enter email:\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str = in.next();
        user.setEmail(str);
    }


}
