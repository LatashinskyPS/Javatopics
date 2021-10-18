package com.latashinsky.java.topics.helpers;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.UserTypes;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.MyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class TransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(TransactionManager.class);
    private static TransactionManager transactionManager;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    private static final MyRepository<Transaction> transactionRepository = Factory.getInstance().getRepository(Transaction.class);

    private TransactionManager() {
    }

    public static TransactionManager getInstance() {
        if (transactionManager == null) {
            transactionManager = new TransactionManager();
        }
        return transactionManager;
    }

    public static void doTransactionWithoutCheck(Account accountFrom, Account accountTo,
                                                 BigDecimal value, HashMap<String, BigDecimal> currencyExchangeRate) {
        BigDecimal ratio = new BigDecimal("1");
        if (!accountFrom.getBank().equals(accountTo.getBank())) {
            if (accountFrom.getUser().getUserType().equals(UserTypes.USUAL)) {
                ratio = ratio.multiply(BigDecimal.valueOf(1).subtract(
                        accountFrom.getBank().getUsualCommission()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
            } else {
                ratio = ratio.multiply(BigDecimal.valueOf(1).subtract(
                        accountFrom.getBank().getLegalCommission()
                                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));
            }
        }
        ratio = ratio.divide(currencyExchangeRate.get(accountFrom.getCurrency()), 2, RoundingMode.HALF_UP)
                .multiply(currencyExchangeRate.get(accountTo.getCurrency()));
        accountFrom.setBalance(accountFrom.getBalance().subtract(value));
        BigDecimal resultTransactionMoney = value.multiply(ratio);
        accountTo.setBalance(accountTo.getBalance().add(resultTransactionMoney));
        MyRepository<Account> myRepository = Factory.getInstance().getRepository(Account.class);
        myRepository.save(accountFrom);
        myRepository.save(accountTo);
        Transaction transaction = Factory.getInstance().getEntity(Transaction.class);
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setDate(new Date());
        transaction.setValue(value);
        transactionRepository.save(transaction);
    }

    public void doTransaction(Account accountFrom, Account accountTo, BigDecimal value) {
        reentrantLock.lock();
        try {
            if (accountFrom.getBalance().compareTo(value) >= 0) {
                HashMap<String, BigDecimal> currencyExchangeRate
                        = Factory.getInstance().getCurrencyExchangeRateHelper().getCurrencyExchangeRate();
                if (!currencyExchangeRate.containsKey(accountFrom.getCurrency())
                        || !currencyExchangeRate.containsKey(accountTo.getCurrency())) {
                    logger.info("We don't know this currency!\n");
                    return;
                }
                doTransactionWithoutCheck(accountFrom, accountTo, value, currencyExchangeRate);
            } else {
                logger.info("Error!Not enough money!\n");
            }
        } finally {
            reentrantLock.unlock();
        }
    }
}
