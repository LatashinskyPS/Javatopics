package com.latashinsky.java.topics.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

public interface Bank {
    Logger logger = LoggerFactory.getLogger(Bank.class);

    String getName();

    void setName(String name);

    BigDecimal getUsualCommission();

    void setUsualCommission(BigDecimal usualCommission);

    BigDecimal getLegalCommission();

    void setLegalCommission(BigDecimal legalCommission);

    int getId();

    void setId(int id);

    List<? extends Account> getAccounts();

    void setAccounts(List<? extends Account> accounts);
}
