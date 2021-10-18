package com.latashinsky.java.topics.entities.database;

import com.latashinsky.java.topics.entities.CurrencyExchange;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "currencies_exchange")
public class DataBaseCurrencyExchange implements CurrencyExchange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "value_to")
    private BigDecimal valueTo;

    @Column(name = "value_from")
    private BigDecimal valueIn;

    @Column(name = "currency_id", updatable = false, insertable = false)
    private int currencyId;

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
