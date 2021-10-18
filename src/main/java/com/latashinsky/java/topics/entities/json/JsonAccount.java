package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class JsonAccount implements Account {

    private int id;

    private int currencyId;

    private int bankId;

    private int userId;

    private Currency currency;

    private BigDecimal balance;

    private Bank bank;

    @JsonIgnore
    private User user;

    @JsonIgnore
    private List<Transaction> transactionsFrom;

    @JsonIgnore
    private List<Transaction> transactionsTo;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<? extends Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    @SuppressWarnings("unchecked")
    public void setTransactionsFrom(List<? extends Transaction> transactionsFrom) {
        this.transactionsFrom = (List<Transaction>) transactionsFrom;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    @SuppressWarnings("unchecked")
    public void setTransactionsTo(List<? extends Transaction> transactionsTo) {
        this.transactionsTo = (List<Transaction>) transactionsTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
