package by.latashinsky.controllers;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Transaction;
import by.latashinsky.entities.User;
import by.latashinsky.factory.Factory;
import by.latashinsky.models.Constants;
import by.latashinsky.models.MyListConverter;
import by.latashinsky.repositories.MyRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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


    public boolean attemptToExecuteTheCommand(String s, User user) {
        switch (s.toLowerCase(Locale.ROOT)) {
            case "exit": {
                return true;
            }
            case "show": {
                System.out.println(MyListConverter.convert(findTransactions(user)));
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
                System.out.println("Unknown command! Try help.");
                return false;
            }
        }
    }

    private List<Transaction> findTransactions(User user) {
        List<Transaction> transactionList = new LinkedList<Transaction>();
        MyRepository<Transaction> repository = (MyRepository<Transaction>) Factory.getInstance().getRepository(Transaction.class);
        MyRepository<Account> repositoryAccount = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);
        repositoryAccount.findAll().stream().filter(r -> r.getIdUser() == user.getId()).
                forEach(r -> transactionList.addAll(
                        repository.findAll().stream().filter(a -> a.getIdAccountTo() == user.getId()
                                || a.getIdAccountFrom() == user.getId()).collect(Collectors.toList())));
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
                Date date;
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
                Date date;
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
        if (transactions.isEmpty()) {
            System.out.println("Can't to find.");
        }
        System.out.println(MyListConverter.convert(transactions));
    }

    @Override
    public void help() {
        System.out.println(
                "filer -вывести транзакции пользователя за указанный период\n" +
                        "show - вывести список всех транзкций пользователя\n" +
                        "exit - перейти к предыдущему меню\n" +
                        "help - вывести данное меню"
        );
    }
}
