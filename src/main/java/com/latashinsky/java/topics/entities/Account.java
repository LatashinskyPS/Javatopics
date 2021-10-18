package com.latashinsky.java.topics.entities;

import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.utils.SelectHelpUtil;

import java.math.BigDecimal;
import java.util.List;

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

    String getCurrency();

    void setCurrency(String currency);

    int getId();

    void setId(int id);

    Bank getBank();

    void setBank(Bank bank);

    int getBankId();

    void setBankId(int bankId);

    int getUserId();

    void setUserId(int userId);
}
