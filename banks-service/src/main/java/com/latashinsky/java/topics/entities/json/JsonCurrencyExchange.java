package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.CurrencyExchange;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class JsonCurrencyExchange implements CurrencyExchange {
    private UUID id = UUID.randomUUID();

    private Date date;

    private BigDecimal rate;

    private UUID currencyId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(UUID currencyId) {
        this.currencyId = currencyId;
    }
}
