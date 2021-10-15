package by.latashinsky.java.topics.entities.factories;

import by.latashinsky.java.topics.entities.*;
import by.latashinsky.java.topics.entities.json.*;

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
        if (clazz.equals(Currency.class)) {
            return (T) new JsonCurrency();
        }
        if (clazz.equals(User.class)) {
            return (T) new JsonUser();
        }
        if (clazz.equals(Transaction.class)) {
            return (T) new JsonTransaction();
        }
        return null;
    }
}
