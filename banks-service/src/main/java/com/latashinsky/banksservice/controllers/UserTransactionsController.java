package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Transaction;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.helpers.CollectionConverter;
import com.latashinsky.banksservice.repositories.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class UserTransactionsController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserTransactionsController.class);
    private static UserTransactionsController userTransactionsController;

    private UserTransactionsController() {
    }

    public static UserTransactionsController getInstance() {
        if (userTransactionsController == null) {
            userTransactionsController = new UserTransactionsController();
        }
        return userTransactionsController;
    }


    public boolean attemptToExecuteTheCommand(String s, User<?> user) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit": {
                return true;
            }
            case "show": {
                logger.info(CollectionConverter.convert(findTransactions(user)) + "\n");
                return false;
            }
            case "filter": {
                filter(user);
                return false;
            }
            case "help": {
                help();
                return false;
            }
            default: {
                logger.info("Unknown command! Try help.\n");
                return false;
            }
        }
    }

    private List<Transaction<?>> findTransactions(User<?> user) {
        TransactionRepository<Transaction<?>> transactionRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        return (List<Transaction<?>>) transactionRepository.getTransactionsUser(user);
    }

    public void filter(User<?> user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str;
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        List<Transaction<?>> transactions = findTransactions(user);
        while (true) {
            logger.info("Enter max date(exit to cancel):\n");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DATE, str)) {
                Date date;
                try {
                    date = formatter.parse(str);
                    Date finalDate = date;
                    transactions.removeIf(r -> r.getDate().after(finalDate));
                    break;
                } catch (ParseException e) {
                    logger.info(e.getMessage());
                }
            }
            if ("exit".equals(str)) {
                return;
            }
            logger.info("Invalid input!\n");
        }
        while (true) {
            logger.info("Enter mid date(exit to cancel):\n");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DATE, str)) {
                Date date;
                try {
                    date = formatter.parse(str);
                    Date finalDate = date;
                    transactions.removeIf(r -> r.getDate().before(finalDate));
                    break;
                } catch (ParseException e) {
                    logger.info(e.getMessage());
                }
            }
            if ("exit".equals(str)) {
                return;
            }
            logger.info("Invalid input!\n");
        }
        if (transactions.isEmpty()) {
            logger.info("Can't to find.\n");
        }
        logger.info(CollectionConverter.convert(transactions) + "\n");
    }

    @Override
    public void help() {
        logger.info(
                "filer -вывести транзакции пользователя за указанный период\n"
                        + "show - вывести список всех транзкций пользователя\n"
                        + "exit - перейти к предыдущему меню\n"
                        + "help - вывести данное меню\n"
        );
    }
}
