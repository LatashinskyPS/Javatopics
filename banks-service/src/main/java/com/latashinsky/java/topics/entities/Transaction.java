package com.latashinsky.java.topics.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface Transaction<T extends Account<?, ?, ?, ?>> {
    BigDecimal getValue();

    void setValue(BigDecimal value);

    UUID getId();

    void setId(UUID id);

    @JsonIgnore
    T getAccountFrom();

    void setAccountFrom(T accountFrom);

    @JsonIgnore
    T getAccountTo();

    void setAccountTo(T accountTo);

    Date getDate();

    void setDate(Date date);

    UUID getAccountFromId();

    void setAccountFromId(UUID accountFromId);

    UUID getAccountToId();

    void setAccountToId(UUID accountToId);
}
