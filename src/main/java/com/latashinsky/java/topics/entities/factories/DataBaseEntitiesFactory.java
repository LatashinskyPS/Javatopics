package com.latashinsky.java.topics.entities.factories;

import com.latashinsky.java.topics.entities.*;
import com.latashinsky.java.topics.entities.database.*;

public class DataBaseEntitiesFactory implements EntitiesFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getEntity(Class<T> clazz) {
        if (clazz.equals(Account.class)) {
            return (T) new DataBaseAccount();
        }
        if (clazz.equals(Bank.class)) {
            return (T) new DataBaseBank();
        }
        if (clazz.equals(Currency.class)) {
            return (T) new DataBaseCurrency();
        }
        if (clazz.equals(User.class)) {
            return (T) new DataBaseUser();
        }
        if (clazz.equals(Transaction.class)) {
            return (T) new DataBaseTransaction();
        }
        return null;
    }
}
