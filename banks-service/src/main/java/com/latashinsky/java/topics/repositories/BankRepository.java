package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.Bank;

public interface BankRepository<T extends Bank<?>> extends MyRepository<T> {
}
