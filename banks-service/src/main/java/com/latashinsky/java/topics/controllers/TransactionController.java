package com.latashinsky.java.topics.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.latashinsky.java.topics.helpers.TransactionManager;
import com.latashinsky.java.topics.interfaces.UserTransactionUi;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import com.latashinsky.java.topics.repositories.UserRepository;
import com.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TransactionController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static TransactionController transactionController;
    private final TransactionRepository<Transaction<?>> transactionRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private TransactionController() {
    }

    public static TransactionController getInstance() {
        if (transactionController == null) {
            transactionController = new TransactionController();
        }
        return transactionController;
    }

    public boolean attemptToExecuteTheCommand(String s) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit": {
                return true;
            }
            case "help": {
                help();
                return false;
            }
            case "new": {
                createTransaction();
                return false;
            }
            case "show": {
                show();
                return false;
            }
            case "user": {
                read();
                return false;
            }
            default: {
                logger.info("Unknown command! Try help.\n");
                return false;
            }
        }
    }

    public void read() {
        User<Account<?, ?, ?, ?>> user = SelectHelpUtil.selectUser();
        if (user != null) {
            UserTransactionUi.run(user);
        }
    }

    public void show() {
        logger.info(MyListConverter.convert(transactionRepository.findAll()));
    }

    public void createTransaction() {
        logger.info("Select account from:\n");
        Account<?, ?, ?, ?> accountFrom = SelectHelpUtil.selectAccount();
        if (accountFrom == null) {
            return;
        }
        logger.info("Select account to:\n");
        Account<?, ?, ?, ?> accountTo = SelectHelpUtil.selectAccount();
        if (accountTo == null) {
            return;
        }
        if (accountFrom == accountTo) {
            logger.info("Error! Accounts equal!\n");
            return;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(0);
        String str;
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        do {
            logger.info("Enter sum(exit to cancel)\n");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str)) {
                bigDecimal = new BigDecimal(str);
                break;
            }
        } while (!"exit".equals(str));
        TransactionManager.doTransaction(accountFrom, accountTo, bigDecimal);
        User<?> userFrom = userRepository.findById(accountFrom.getUserId());
        User<?> userTo = userRepository.findById(accountTo.getUserId());
        String amount = bigDecimal + " " + accountFrom.getCurrency().toString();
        if (userFrom != null) {
            Constants.messageService.sendMessage(userFrom, "transaction from:\n" + accountFrom
                    + "\nAmount:" + amount, "transactions.create");
        }
        if (userTo != null) {
            Constants.messageService.sendMessage(userTo, "transaction to:\n" + accountTo
                    + "\nAmount:" + amount, "transactions.create");
        }
    }

    @Override
    public void help() {
        logger.info(
                "user - перейти к меню для получения более подробной информации по транзакциям пользователя\n"
                        + "show - вывести список всех транзкций\n"
                        + "new - провести новую транзакцию\n"
                        + "exit - перейти к предыдущему меню\n"
                        + "help - вывести данное меню\n"
        );
    }
}
