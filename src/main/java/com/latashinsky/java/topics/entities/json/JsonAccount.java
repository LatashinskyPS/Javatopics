package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.database.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class JsonAccount implements Account {
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = (JsonUser) user;
    }

    @Override
    public List<? extends Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setTransactionsFrom(List<? extends Transaction> transactionsFrom) {
        this.transactionsFrom = (List<JsonTransaction>) transactionsFrom;
    }

    @Override
    public List<? extends Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setTransactionsTo(List<? extends Transaction> transactionsTo) {
        this.transactionsTo = (List<JsonTransaction>) transactionsTo;
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
    public void setCurrency(Currency currency) {
        this.currency = (JsonCurrency) currency;
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
    public void setBank(Bank bank) {
        this.bank = (JsonBank) bank;
    }
}
