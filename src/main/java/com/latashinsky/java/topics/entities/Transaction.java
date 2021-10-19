package com.latashinsky.java.topics.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface Transaction {
    BigDecimal getValue();

    void setValue(BigDecimal value);

    UUID getId();

    void setId(UUID id);

    Account getAccountFrom();

    <T extends Account> void setAccountFrom(T accountFrom);

    Account getAccountTo();

    <T extends Account> void setAccountTo(T accountTo);

    Date getDate();

    void setDate(Date date);

    UUID getAccountFromId();

    void setAccountFromId(UUID accountFromId);

    UUID getAccountToId();

    void setAccountToId(UUID accountToId);
}
