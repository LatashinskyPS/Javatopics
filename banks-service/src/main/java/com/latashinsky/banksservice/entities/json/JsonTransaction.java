package com.latashinsky.banksservice.entities.json;

import com.latashinsky.banksservice.entities.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class JsonTransaction implements Transaction<JsonAccount> {
    private UUID id = UUID.randomUUID();

    private UUID accountFromId = UUID.randomUUID();

    private UUID accountToId = UUID.randomUUID();

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public JsonAccount getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(JsonAccount accountFrom) {
        this.accountFrom = accountFrom;
    }

    public JsonAccount getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(JsonAccount accountTo) {
        this.accountTo = accountTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(UUID accountFromId) {
        this.accountFromId = accountFromId;
    }

    public UUID getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(UUID accountToId) {
        this.accountToId = accountToId;
    }
}
