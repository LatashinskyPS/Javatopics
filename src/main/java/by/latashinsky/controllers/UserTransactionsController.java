package by.latashinsky.controllers;

import by.latashinsky.entities.Transaction;
import by.latashinsky.entities.User;
import by.latashinsky.factory.RepositoryFactory;
import by.latashinsky.models.Constants;
import by.latashinsky.models.MyListConverter;
import by.latashinsky.repositories.TransactionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class UserTransactionsController implements Controller {
    private static UserTransactionsController userTransactionsController;

    private UserTransactionsController() {
    }

    public static UserTransactionsController getInstance() {
        if (userTransactionsController == null) {
            userTransactionsController = new UserTransactionsController();
        }
        return userTransactionsController;
    }

    private final TransactionRepository transactionRepository = TransactionRepository.getInstance();
    private final RepositoryFactory repositoryFactory = new RepositoryFactory();

    public boolean attemptToExecuteTheCommand(String s, User user) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit" -> {
                return true;
            }
            case "show" -> {
                System.out.println(MyListConverter.convert(findTransactions(user)));
                return false;
            }
            case "filter" -> {
                filter(user);
                return false;
            }
            case "help" -> {
                help();
                return false;
            }
            default -> {
                System.out.println("Unknown command! Try help.");
                return false;
            }
        }
    }

    private List<Transaction> findTransactions(User user) {
        List<Transaction> transactionList = new LinkedList<Transaction>();
        user.getAccounts().forEach(r -> {
            transactionList.addAll(r.getTransactionsFrom());
            transactionList.addAll(r.getTransactionsTo());
        });
        return transactionList;
    }

    public void filter(User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str;
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        List<Transaction> transactions = findTransactions(user);
        while (true) {
            System.out.println("Enter max date(exit to cancel):");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DATE, str)) {
                Date date = null;
                try {
                    date = formatter.parse(str);
                    Date finalDate = date;
                    transactions.removeIf(r -> r.getDate().after(finalDate));
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if ("exit".equals(str)) return;
            System.out.println("Invalid input!");
        }
        while (true) {
            System.out.println("Enter mid date(exit to cancel):");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DATE, str)) {
                Date date = null;
                try {
                    date = formatter.parse(str);
                    Date finalDate = date;
                    transactions.removeIf(r -> r.getDate().before(finalDate));
                    break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if ("exit".equals(str)) return;
            System.out.println("Invalid input!");
        }
        if(transactions.isEmpty()){
            System.out.println("Can't to find.");
        }
        System.out.println(MyListConverter.convert(transactions));
    }

    @Override
    public void help() {
        System.out.println(
                """
                        filer -вывести транзакции пользователя за указанный период
                        show - вывести список всех транзкций пользователя
                        exit - перейти к предыдущему меню
                        help - вывести данное меню"""
        );
    }
}
