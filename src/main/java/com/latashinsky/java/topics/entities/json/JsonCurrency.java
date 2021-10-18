package com.latashinsky.java.topics.entities.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;

import java.util.List;

public class JsonCurrency implements Currency {
    private int id;

    private String name;

    @JsonIgnore
    private List<CurrencyExchange> currencyExchanges;

    public List<CurrencyExchange> getCurrencyExchanges() {
        return currencyExchanges;
    }

    @SuppressWarnings("unchecked")
    public void setCurrencyExchanges(List<? extends CurrencyExchange> currencyExchanges) {
        this.currencyExchanges = (List<CurrencyExchange>) currencyExchanges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
