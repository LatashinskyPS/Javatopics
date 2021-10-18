package com.latashinsky.java.topics.repositories.factories;

import com.latashinsky.java.topics.entities.*;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.repositories.database.DataBaseCurrencyExchangeRepository;
import com.latashinsky.java.topics.repositories.json.*;

public class JsonRepositoryFactory implements RepositoryFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> MyRepository<T> getRepository(Class<T> clazz) {
        if (clazz.equals(Bank.class)) {
            return (MyRepository<T>) JsonBankRepository.getInstance();
        }
        if (clazz.equals(Account.class)) {
            return (MyRepository<T>) JsonAccountRepository.getInstance();
        }
        if (clazz.equals(User.class)) {
            return (MyRepository<T>) JsonUserRepository.getInstance();
        }
        if (clazz.equals(Transaction.class)) {
            return (MyRepository<T>) JsonTransactionRepository.getInstance();
        }
        if (clazz.equals(Currency.class)) {
            return (MyRepository<T>) JsonCurrencyRepository.getInstance();
        }
        if (clazz.equals(CurrencyExchange.class)) {
            return (MyRepository<T>) JsonCurrencyExchangeRepository.getInstance();
        }
        return null;
    }
}
