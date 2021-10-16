package by.latashinsky.java.topics.entities;

import by.latashinsky.java.topics.helpers.Constants;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface Account {
    Logger logger = LoggerFactory.getLogger(Account.class);

    User getUser();

    void setUser(User user);

    List<? extends Transaction> getTransactionsFrom();

    void setTransactionsFrom(List<? extends Transaction> transactionsFrom);

    List<? extends Transaction> getTransactionsTo();

    void setTransactionsTo(List<? extends Transaction> transactionsTo);

    BigDecimal getBalance();

    void setBalance(BigDecimal balance);

    default boolean editUser() {
        User user = SelectHelpUtil.selectUser();
        if (user == null) {
            return false;
        }
        setUser(user);
        return true;
    }


    String getCurrency();

    void setCurrency(String currency);

    int getId();

    void setId(int id);

    Bank getBank();

    void setBank(Bank bank);

    int getBankId();

    void setBankId(int bankId);

    int getUserId();

    void setUserId(int userId);

    default boolean editBank() {
        Bank bank = SelectHelpUtil.selectBank();
        if (bank == null) {
            return false;
        }
        setBank(bank);
        return true;
    }

    default boolean editBalance() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            logger.info("Enter value of balance(exit to return to menu):");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str)) {
                setBalance(new BigDecimal(str));
                return true;
            }
            if ("exit".equals(str)) {
                return false;
            }
            logger.info("Invalid input!\n");
        }
    }

    default boolean editCurrency() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            logger.info("Enter name of currency:");
            str = in.next();
            if (str.length() < 4) {
                setCurrency(str);
                return true;
            }
            if ("exit".equals(str)) {
                return false;
            }
            logger.info("Invalid input!\n");
        }
    }

}
