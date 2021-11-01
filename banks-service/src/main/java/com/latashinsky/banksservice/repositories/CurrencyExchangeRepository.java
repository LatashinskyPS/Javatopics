package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.CurrencyExchange;

import java.util.Collection;

public interface CurrencyExchangeRepository<T extends CurrencyExchange> extends MyRepository<T> {
    CurrencyExchange findByCurrencyWhereDateIsNow(Currency<?> currency);

    Collection<CurrencyExchange> findByCurrency(Currency<?> currency);
}
