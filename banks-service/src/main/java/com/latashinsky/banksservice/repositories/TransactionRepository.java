package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.Transaction;
import com.latashinsky.banksservice.entities.User;

import java.util.Collection;

public interface TransactionRepository<T extends Transaction<?>> extends MyRepository<T> {
    Collection<T> getTransactionsUser(User<?> user);
}
