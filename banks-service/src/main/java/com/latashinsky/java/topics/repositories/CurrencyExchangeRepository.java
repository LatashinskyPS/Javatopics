package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;

import java.util.Collection;

public interface CurrencyExchangeRepository<T extends CurrencyExchange> extends MyRepository<T> {
    CurrencyExchange findByCurrencyWhereDateIsNow(Currency<?> currency);

    Collection<CurrencyExchange> findByCurrency(Currency<?> currency);
}
