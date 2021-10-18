package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.Currency;

public interface CurrencyRepository<T extends Currency> extends MyRepository<T> {
    Currency findByName(String name);
}
