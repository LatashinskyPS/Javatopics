package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.Currency;

public interface CurrencyRepository<T extends Currency<?>> extends MyRepository<T> {
    Currency<?> findByName(String name);
}
