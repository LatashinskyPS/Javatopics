package com.latashinsky.java.topics.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface Bank {
    Logger logger = LoggerFactory.getLogger(Bank.class);

    String getName();

    void setName(String name);

    BigDecimal getUsualCommission();

    void setUsualCommission(BigDecimal usualCommission);

    BigDecimal getLegalCommission();

    void setLegalCommission(BigDecimal legalCommission);

    UUID getId();

    void setId(UUID id);

    List<? extends Account> getAccounts();

    void setAccounts(List<? extends Account> accounts);
}
