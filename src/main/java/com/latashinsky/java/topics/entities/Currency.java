package com.latashinsky.java.topics.entities;

import java.util.List;

public interface Currency {
    List<? extends CurrencyExchange> getCurrencyExchanges();

    void setCurrencyExchanges(List<? extends CurrencyExchange> currencyExchanges);

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);
}
