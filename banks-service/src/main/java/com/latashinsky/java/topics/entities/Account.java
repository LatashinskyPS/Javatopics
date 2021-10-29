package com.latashinsky.java.topics.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public interface Account<Z extends Transaction<?>, X extends Currency<?>, C extends Bank<?>, Y extends User<?>> {
    Logger logger = LoggerFactory.getLogger(Account.class);

    @JsonIgnore
    Y getUser();

    void setUser(Y user);

    @JsonIgnore
    List<Z> getTransactionsFrom();

    void setTransactionsFrom(List<Z> transactionsFrom);

    @JsonIgnore
    List<Z> getTransactionsTo();

    void setTransactionsTo(List<Z> transactionsTo);

    BigDecimal getBalance();

    void setBalance(BigDecimal balance);

    void setCurrency(X currency);

    UUID getId();

    void setId(UUID id);

    UUID getBankId();

    void setBankId(UUID bankId);

    UUID getUserId();

    void setUserId(UUID userId);

    UUID getCurrencyId();

    void setCurrencyId(UUID currencyId);

    @JsonIgnore
    X getCurrency();

    @JsonIgnore
    C getBank();

    void setBank(C bank);
}
