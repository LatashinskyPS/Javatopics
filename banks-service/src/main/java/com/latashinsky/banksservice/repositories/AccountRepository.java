package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.User;

import java.util.Collection;

public interface AccountRepository<T extends Account<?, ?, ?, ?>> extends  MyRepository<T> {
    Collection<T> getAccountsBank(Bank<?> bank);

    Collection<T> getAccountsUser(User<?> user);
}
