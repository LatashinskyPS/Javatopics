package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class JsonAccount implements Account<JsonTransaction, JsonCurrency, JsonBank, JsonUser> {
    private UUID id = UUID.randomUUID();

    private UUID bankId = UUID.randomUUID();

    private UUID userId = UUID.randomUUID();

    private UUID currencyId = UUID.randomUUID();

    private BigDecimal balance;

    @JsonIgnore
    private JsonCurrency currency;

    @JsonIgnore
    protected JsonBank bank;

    @JsonIgnore
    private JsonUser user;

    @JsonIgnore
    private List<JsonTransaction> transactionsFrom;

    @JsonIgnore
    private List<JsonTransaction> transactionsTo;

    public JsonAccount() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonAccount account = (JsonAccount) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Account id:%s\nBank:%s\nBalance:%s\nCurrency:%s",
                id, bank.getName().toUpperCase(Locale.ROOT), balance, currency);
    }


    @Override
    public JsonUser getUser() {
        return user;
    }

    @Override
    public void setUser(JsonUser user) {
        this.user = user;
    }

    @Override
    public List<JsonTransaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    @Override
    public void setTransactionsFrom(List<JsonTransaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    @Override
    public List<JsonTransaction> getTransactionsTo() {
        return transactionsTo;
    }

    @Override
    public void setTransactionsTo(List<JsonTransaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public void setCurrency(JsonCurrency currency) {
        this.currency = currency;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getBankId() {
        return bankId;
    }

    @Override
    public void setBankId(UUID bankId) {
        this.bankId = id;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public UUID getCurrencyId() {
        return currencyId;
    }

    @Override
    public void setCurrencyId(UUID currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public JsonCurrency getCurrency() {
        return currency;
    }

    @Override
    public JsonBank getBank() {
        return bank;
    }

    @Override
    public void setBank(JsonBank bank) {
        this.bank = bank;
    }
}
