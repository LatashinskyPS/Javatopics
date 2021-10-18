package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.User;

import java.util.Collection;

public interface AccountRepository<T extends Account> extends  MyRepository<T> {
    Collection<T> getAccountsBank(Bank bank);

    Collection<T> getAccountsUser(User user);
}
