package by.latashinsky.models;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Transaction;
import by.latashinsky.entities.UserTypes;
import by.latashinsky.factory.DataBaseRepositoryFactory;
import by.latashinsky.factory.Factory;
import by.latashinsky.repositories.MyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager {
    private static TransactionManager transactionManager;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final MyRepository<Transaction> transactionRepository = (MyRepository<Transaction>) Factory.getInstance().getRepository(Transaction.class);

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (transactionManager == null) {
            transactionManager = new TransactionManager();
        }
        return transactionManager;
    }

    public void doTransaction(Account accountFrom, Account accountTo, BigDecimal value) {
        reentrantLock.lock();
        try {
            if (accountFrom.getBalance().compareTo(value) >= 0) {
                HashMap<String, BigDecimal> currencyExchangeRate
                        = Factory.getInstance().getCurrencyExchangeRateHelper().getCurrencyExchangeRate();
                if (!currencyExchangeRate.containsKey(accountFrom.getCurrency())
                        || !currencyExchangeRate.containsKey(accountTo.getCurrency())) {
                    System.out.println("We don't this currency!");
                    return;
                }
                BigDecimal ratio = new BigDecimal("1");
                if (!accountFrom.getBank().equals(accountTo.getBank())) {
                    if (accountFrom.getUser().getUserType().equals(UserTypes.USUAL)) {
                        ratio = ratio.multiply(BigDecimal.valueOf(1).subtract(
                                accountFrom.getBank().getUsualCommission()
                                        .divide(BigDecimal.valueOf(100))));
                    } else {
                        ratio = ratio.multiply(BigDecimal.valueOf(1).subtract(
                                accountFrom.getBank().getLegalCommission()
                                        .divide(BigDecimal.valueOf(100))));
                    }
                }
                ratio = ratio.divide(currencyExchangeRate.get(accountFrom.getCurrency()), 2, RoundingMode.HALF_UP)
                        .multiply(currencyExchangeRate.get(accountTo.getCurrency()));
                accountFrom.setBalance(accountFrom.getBalance().subtract(value));
                BigDecimal resultTransactionMoney = value.multiply(ratio);
                accountTo.setBalance(accountTo.getBalance().add(resultTransactionMoney));
                MyRepository<Account> myRepository = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);
                myRepository.save(accountFrom);
                myRepository.save(accountTo);
                Transaction transaction = new Transaction();
                transaction.setAccountFrom(accountFrom);
                transaction.setAccountTo(accountTo);
                transaction.setDate(new Date());
                transaction.setValue(value);
                transactionRepository.save(transaction);
            } else {
                System.out.println("Error!Not enough money!");
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
