package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class JsonTransaction implements Transaction {
    private int id;

    private int accountFromId;

    private int accountToId;

    private Date date;

    private BigDecimal value;

    @JsonIgnore
    private JsonAccount accountFrom;

    @JsonIgnore
    private JsonAccount accountTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonTransaction that = (JsonTransaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return
                "id=" + id
                        + ", accountFrom=" + accountFrom.getId()
                        + ", accountTo=" + accountTo.getId()
                        + ", date=" + date
                        + ", value=" + value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = (JsonAccount) accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = (JsonAccount) accountTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int accountFromId) {
        this.accountFromId = accountFromId;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int accountToId) {
        this.accountToId = accountToId;
    }
}
