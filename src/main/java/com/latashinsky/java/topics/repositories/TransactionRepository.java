package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;

import java.util.Collection;

public interface TransactionRepository<T extends Transaction> extends MyRepository<T> {
    Collection<T> getTransactionsUser(User user);
}
