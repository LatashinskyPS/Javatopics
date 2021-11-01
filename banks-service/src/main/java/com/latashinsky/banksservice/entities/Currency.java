package com.latashinsky.banksservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public interface Currency<T extends CurrencyExchange> {
    @JsonIgnore
    List<T> getCurrencyExchanges();

    void setCurrencyExchanges(List<T> currencyExchanges);

    UUID getId();

    void setId(UUID id);

    String getName();

    void setName(String name);
}
