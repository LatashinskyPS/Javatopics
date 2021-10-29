package com.latashinsky.java.topics.services;

import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final BankService instance = new BankService();

    private BankService() {
    }

    public static BankService getInstance() {
        return instance;
    }

    public void editName(Bank<?> bank) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.print("Enter name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                bank.setName(str);
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    public void editUsualCommission(Bank<?> bank) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter usual commission:");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                logger.info(str + "\n");
                bank.setUsualCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    public void editLegalCommission(Bank<?> bank) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter legal commission:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                bank.setLegalCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }

    }
}
