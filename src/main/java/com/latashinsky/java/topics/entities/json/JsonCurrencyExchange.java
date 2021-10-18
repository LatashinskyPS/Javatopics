package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.CurrencyExchange;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

public class JsonCurrencyExchange implements CurrencyExchange {
    private int id;

    private String name;

    private Date date;

    private BigDecimal valueTo;

    private BigDecimal valueIn;

    private int currencyId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValueTo() {
        return valueTo;
    }

    public void setValueTo(BigDecimal valueTo) {
        this.valueTo = valueTo;
    }

    public BigDecimal getValueIn() {
        return valueIn;
    }

    public void setValueIn(BigDecimal valueIn) {
        this.valueIn = valueIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }
}
