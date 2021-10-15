package by.latashinsky.java.topics.entities;

import by.latashinsky.java.topics.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public interface User {
    Logger logger = LoggerFactory.getLogger(User.class);

    List<? extends Account> getAccounts();

    void setAccounts(List<? extends Account> accounts);

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    UserTypes getUserType();

    void setUserType(UserTypes userType);

    default void editName() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter user name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                this.setName(str);
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    default void editUserType() {
        logger.info("Types:\n1)Legal\n2)Usual\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter number of type:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                if (index == 1) {
                    this.setUserType(UserTypes.LEGAL);
                    return;
                }
                if (index == 2) {
                    this.setUserType(UserTypes.USUAL);
                    return;
                }
            }
            logger.info("Invalid input!\n");
        }
    }
}
