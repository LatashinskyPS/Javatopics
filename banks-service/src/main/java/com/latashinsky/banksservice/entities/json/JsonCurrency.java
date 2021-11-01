package com.latashinsky.banksservice.entities.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.banksservice.entities.Currency;

import java.util.List;
import java.util.UUID;

public class JsonCurrency implements Currency<JsonCurrencyExchange> {
    private UUID id = UUID.randomUUID();

    private String name;

    @JsonIgnore
    private List<JsonCurrencyExchange> currencyExchanges;

    public List<JsonCurrencyExchange> getCurrencyExchanges() {
        return currencyExchanges;
    }

    public void setCurrencyExchanges(List<JsonCurrencyExchange> currencyExchanges) {
        this.currencyExchanges = currencyExchanges;
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

    @Override
    public String toString() {
        return name;
    }
}
