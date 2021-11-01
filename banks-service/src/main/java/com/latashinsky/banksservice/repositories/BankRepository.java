package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.Bank;

public interface BankRepository<T extends Bank<?>> extends MyRepository<T> {
}
