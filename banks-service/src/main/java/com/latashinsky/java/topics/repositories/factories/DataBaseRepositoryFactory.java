package com.latashinsky.java.topics.repositories.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.repositories.*;
import com.latashinsky.java.topics.repositories.database.*;

import java.lang.reflect.Type;

public class DataBaseRepositoryFactory implements RepositoryFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getRepository(TypeReference<T> typeReference) {
        Type type = typeReference.getType();
        String str = type.getTypeName().split("<")[0];
        Class<?> clazz;
        try {
            clazz = Class.forName(str);
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (clazz.equals(BankRepository.class)) {
            return (T) DataBaseBankRepository.getInstance();
        }
        if (clazz.equals(AccountRepository.class)) {
            return (T) DataBaseAccountRepository.getInstance();
        }
        if (clazz.equals(UserRepository.class)) {
            return (T) DataBaseUserRepository.getInstance();
        }
        if (clazz.equals(TransactionRepository.class)) {
            return (T) DataBaseTransactionRepository.getInstance();
        }
        if (clazz.equals(CurrencyRepository.class)) {
            return (T) DataBaseCurrencyRepository.getInstance();
        }
        if (clazz.equals(CurrencyExchangeRepository.class)) {
            return (T) DataBaseCurrencyExchangeRepository.getInstance();
        }
        return null;
    }
}
