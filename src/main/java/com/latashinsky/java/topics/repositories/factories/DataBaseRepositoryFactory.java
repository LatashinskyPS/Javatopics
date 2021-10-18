package com.latashinsky.java.topics.repositories.factories;

import com.latashinsky.java.topics.entities.*;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.repositories.database.*;

public class DataBaseRepositoryFactory implements RepositoryFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> MyRepository<T> getRepository(Class<T> clazz) {
        if (clazz.equals(Bank.class)) {
            return (MyRepository<T>) DataBaseBankRepository.getInstance();
        }
        if (clazz.equals(Account.class)) {
            return (MyRepository<T>) DataBaseAccountRepository.getInstance();
        }
        if (clazz.equals(User.class)) {
            return (MyRepository<T>) DataBaseUserRepository.getInstance();
        }
        if (clazz.equals(Transaction.class)) {
            return (MyRepository<T>) DataBaseTransactionRepository.getInstance();
        }
        if (clazz.equals(Currency.class)) {
            return (MyRepository<T>) DataBaseCurrencyRepository.getInstance();
        }
        if (clazz.equals(CurrencyExchange.class)) {
            return (MyRepository<T>) DataBaseCurrencyExchangeRepository.getInstance();
        }
        return null;
    }
}
