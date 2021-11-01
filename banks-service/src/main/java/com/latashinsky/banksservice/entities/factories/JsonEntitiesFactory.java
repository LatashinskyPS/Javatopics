package com.latashinsky.banksservice.entities.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.*;
import com.latashinsky.banksservice.entities.json.*;

import java.lang.reflect.Type;

public class JsonEntitiesFactory implements EntitiesFactory {
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
            return (T) new JsonAccount();
        }
        if (clazz.equals(Bank.class)) {
            return (T) new JsonBank();
        }
        if (clazz.equals(CurrencyExchange.class)) {
            return (T) new JsonCurrencyExchange();
        }
        if (clazz.equals(User.class)) {
            return (T) new JsonUser();
        }
        if (clazz.equals(Transaction.class)) {
            return (T) new JsonTransaction();
        }
        if (clazz.equals(Currency.class)) {
            return (T) new JsonCurrency();
        }
        return null;
    }
}
