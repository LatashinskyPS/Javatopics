package by.latashinsky.java.topics.entities;

import by.latashinsky.java.topics.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public interface Bank {
    Logger logger = LoggerFactory.getLogger(Bank.class);

    String getName();

    void setName(String name);

    BigDecimal getUsualCommission();

    void setUsualCommission(BigDecimal usualCommission);

    BigDecimal getLegalCommission();

    void setLegalCommission(BigDecimal legalCommission);

    int getId();

    void setId(int id);

    default void editName() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.print("Enter name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                this.setName(str);
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    default void editUsualCommission() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter usual commission:");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                logger.info(str + "\n");
                this.setUsualCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    default void editLegalCommission() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter legal commission:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                this.setLegalCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }

    }

    List<? extends Account> getAccounts();

    void setAccounts(List<? extends Account> accounts);
}
