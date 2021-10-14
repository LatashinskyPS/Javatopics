package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.Constants;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.models.TransactionManager;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.UserTransactionUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TransactionController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private static TransactionController transactionController;
    private final MyRepository<Transaction> transactionRepository = (MyRepository<Transaction>) Factory.getInstance().getRepository(Transaction.class);

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
        User user = SelectHelpUtil.selectUser();
        if (user != null) UserTransactionUI.run(user);
    }

    public void show() {
        logger.info(MyListConverter.convert(transactionRepository.findAll()));
    }

    public void createTransaction() {
        logger.info("Select account from:\n");
        Account accountFrom = SelectHelpUtil.selectAccount();
        if (accountFrom == null) {
            return;
        }
        logger.info("Select account to:\n");
        Account accountTo = SelectHelpUtil.selectAccount();
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
        transactionManager.doTransaction(accountFrom, accountTo, bigDecimal);
    }

    @Override
    public void help() {
        logger.info(
                "user - перейти к меню для получения более подробной информации по транзакциям пользователя\n" +
                        "show - вывести список всех транзкций\n" +
                        "new - провести новую транзакцию\n" +
                        "exit - перейти к предыдущему меню\n" +
                        "help - вывести данное меню\n"
        );
    }
}
