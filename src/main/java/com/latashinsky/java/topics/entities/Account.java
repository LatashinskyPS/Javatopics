package com.latashinsky.java.topics.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.latashinsky.java.topics.entities.database.DataBaseBank;
import com.latashinsky.java.topics.entities.database.DataBaseCurrency;
import com.latashinsky.java.topics.entities.database.DataBaseTransaction;
import com.latashinsky.java.topics.entities.database.DataBaseUser;
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

    void setCurrency(Currency currency);

    UUID getId();

    void setId(UUID id);

    UUID getBankId();

    void setBankId(UUID bankId);

    UUID getUserId();

    void setUserId(UUID userId);

    UUID getCurrencyId();

    void setCurrencyId(UUID currencyId);

    Currency getCurrency();

    Bank getBank();

    void setBank(Bank bank);
}
