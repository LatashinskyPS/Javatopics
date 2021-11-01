package com.latashinsky.banksservice.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.CurrencyExchange;
import com.latashinsky.banksservice.entities.Transaction;
import com.latashinsky.banksservice.entities.UserTypes;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.CurrencyExchangeRepository;
import com.latashinsky.banksservice.repositories.TransactionRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class TransactionManager {
    private static final TransactionRepository<Transaction<?>> transactionRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private static final CurrencyExchangeRepository<CurrencyExchange> currencyExchangeRepository =
            Factory.getInstance().getRepository(new TypeReference<>() {
            });

    public static boolean doTransaction(Account<?, ?, ?, ?> accountFrom, Account<?, ?, ?, ?> accountTo,
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
        AccountRepository<Account<?, ?, ?, ?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        myRepository.save(accountFrom);
        myRepository.save(accountTo);
        Transaction<Account<?, ?, ?, ?>> transaction = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setDate(new Date());
        transaction.setValue(value);
        transactionRepository.save(transaction);
        return true;
    }
}
