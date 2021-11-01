package com.latashinsky.banksservice.entities.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.*;
import com.latashinsky.banksservice.entities.database.*;

import java.lang.reflect.Type;

public class DataBaseEntitiesFactory implements EntitiesFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEntity(TypeReference<T> typeReference) {
        Type type = typeReference.getType();
        String str = type.getTypeName().split("<")[0];
        Class<?> clazz;
        try {
            clazz = Class.forName(str);
        } catch (ClassNotFoundException e) {
            return null;
        }
        if (clazz.equals(Account.class)) {
            return (T) new DataBaseAccount();
        }
        if (clazz.equals(Bank.class)) {
            return (T) new DataBaseBank();
        }
        if (clazz.equals(CurrencyExchange.class)) {
            return (T) new DataBaseCurrencyExchange();
        }
        if (clazz.equals(User.class)) {
            return (T) new DataBaseUser();
        }
        if (clazz.equals(Transaction.class)) {
            return (T) new DataBaseTransaction();
        }
        if (clazz.equals(Currency.class)) {
            return (T) new DataBaseCurrency();
        }
        return null;
    }
}
