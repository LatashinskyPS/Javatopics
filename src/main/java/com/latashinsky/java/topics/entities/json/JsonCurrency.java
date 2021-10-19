package com.latashinsky.java.topics.entities.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;

import java.util.List;
import java.util.UUID;

public class JsonCurrency implements Currency {
    private UUID id = UUID.randomUUID();

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
