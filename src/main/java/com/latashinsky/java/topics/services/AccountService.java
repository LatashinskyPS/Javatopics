package com.latashinsky.java.topics.services;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import com.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final AccountService instance = new AccountService();

    private AccountService() {
    }

    public static AccountService getInstance() {
        return instance;
    }

    public boolean editBank(Account account) {
        Bank bank = SelectHelpUtil.selectBank();
        if (bank == null) {
            return false;
        }
        account.setBank(bank);
        return true;
    }

    public boolean editBalance(Account account) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            logger.info("Enter value of balance(exit to return to menu):");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str)) {
                account.setBalance(new BigDecimal(str));
                return true;
            }
            if ("exit".equals(str)) {
                return false;
            }
            logger.info("Invalid input!\n");
        }
    }

    public boolean editCurrency(Account account) {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            logger.info("Enter name of currency:");
            str = in.next();
            if (str.length() < 4) {
                Currency currency = ((CurrencyRepository<Currency>) Factory.getInstance().getRepository(Currency.class)).findByName(str);
                if (currency != null) {
                    account.setCurrency(currency);
                    return true;
                }
            }
            if ("exit".equals(str)) {
                return false;
            }
            logger.info("Invalid input!\n");
        }
    }

    public boolean editUser(Account account) {
        User user = SelectHelpUtil.selectUser();
        if (user == null) {
            return false;
        }
        account.setUser(user);
        return true;
    }
}
