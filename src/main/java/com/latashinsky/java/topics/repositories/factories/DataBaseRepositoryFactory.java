package com.latashinsky.java.topics.repositories.factories;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.repositories.database.DataBaseAccountRepository;
import com.latashinsky.java.topics.repositories.database.DataBaseBankRepository;
import com.latashinsky.java.topics.repositories.database.DataBaseTransactionRepository;
import com.latashinsky.java.topics.repositories.database.DataBaseUserRepository;

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
        return null;
    }
}
