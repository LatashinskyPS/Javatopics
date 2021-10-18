package com.latashinsky.java.topics.entities.factories;

import com.latashinsky.java.topics.entities.*;
import com.latashinsky.java.topics.entities.json.*;

public class JsonEntitiesFactory implements EntitiesFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEntity(Class<T> clazz) {
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
