package com.latashinsky.java.topics.entities;

import java.util.List;
import java.util.UUID;

public interface Currency {
    List<? extends CurrencyExchange> getCurrencyExchanges();

    void setCurrencyExchanges(List<? extends CurrencyExchange> currencyExchanges);

    UUID getId();

    void setId(UUID id);

    String getName();

    void setName(String name);
}
