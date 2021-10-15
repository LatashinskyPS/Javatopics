package by.latashinsky.java.topics.utils;


import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.helpers.Constants;
import by.latashinsky.java.topics.repositories.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SelectHelpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SelectHelpUtil.class);

    public static Bank selectBank() {
        MyRepository<Bank> myRepository = Factory.getInstance().getRepository(Bank.class);
        myRepository.findAll().forEach(r -> logger.info(r.getId() + ") " + r.getName().toUpperCase(Locale.ROOT) + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of bank(0 to cancel):\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                Bank bank = myRepository.findById(index);
                if (bank != null) {
                    return bank;
                }
            }
            if ("0".equals(str)) break;
            logger.info("Invalid input!\n");
        }
        return null;
    }

    public static User selectUser() {
        MyRepository<User> myRepository = Factory.getInstance().getRepository(User.class);
        myRepository.findAll().forEach(r -> logger.info(r.getId() + ") " + r.getName().toUpperCase(Locale.ROOT) + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of user(0 to cancel):\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                User user = myRepository.findById(index);
                if (user != null) {
                    return user;
                }
            }
            if ("0".equals(str)) break;
            logger.info("Invalid input!\n");
        }
        return null;
    }

    public static Account selectAccount() {
        MyRepository<Account> myRepository = Factory.getInstance().getRepository(Account.class);
        myRepository.findAll().forEach(r -> logger.info(
                r.getId() + ")" +
                        r.getBank().getName() + "->"
                        + r.getUser().getName() + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of account(0 to cancel):\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_INT, str)) {
                int index = Integer.parseInt(str);
                Account account = myRepository.findById(index);
                if (account != null) {
                    return account;
                }
            }
            if ("0".equals(str)) break;
            logger.info("Invalid input!\n");
        }
        return null;
    }
}
