package com.latashinsky.java.topics.helpers;

import com.latashinsky.java.topics.entities.*;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.CurrencyExchangeRepository;
import com.latashinsky.java.topics.repositories.MyRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class TransactionManager {
    private static final MyRepository<Transaction> transactionRepository = Factory.getInstance().getRepository(Transaction.class);
    private static final CurrencyExchangeRepository<CurrencyExchange> currencyExchangeRepository =
            (CurrencyExchangeRepository<CurrencyExchange>) Factory.getInstance().getRepository(CurrencyExchange.class);

    public static boolean doTransaction(Account accountFrom, Account accountTo,
                                        BigDecimal value) {
        CurrencyExchange currencyExchangeFrom =
                currencyExchangeRepository.findByCurrencyWhereDateIsNow(accountFrom.getCurrency());
        CurrencyExchange currencyExchangeTo =
                currencyExchangeRepository.findByCurrencyWhereDateIsNow(accountTo.getCurrency());

        if (currencyExchangeFrom == null
                || currencyExchangeTo == null
                || !(accountFrom.getBalance().compareTo(value) >= 0)) {
            return false;
        }
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
        if (!currencyExchangeFrom.equals(currencyExchangeTo)) {
            ratio = ratio.divide(currencyExchangeFrom.getRate(), 2, RoundingMode.HALF_UP)
                    .multiply(currencyExchangeTo.getRate());
        }
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
        return true;
    }
}
