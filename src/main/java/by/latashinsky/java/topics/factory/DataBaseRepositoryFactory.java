package by.latashinsky.java.topics.factory;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.repositories.*;

public class DataBaseRepositoryFactory implements RepositoryFactory {
    @Override
    public <T> MyRepository<?> getRepository(Class<T> clazz) {
        if (clazz.equals(Bank.class)) {
            return DataBaseBankRepository.getInstance();
        }
        if (clazz.equals(Account.class)) {
            return DataBaseAccountRepository.getInstance();
        }
        if (clazz.equals(User.class)) {
            return DataBaseUserRepository.getInstance();
        }
        if (clazz.equals(Transaction.class)) {
            return DataBaseTransactionRepository.getInstance();
        }
        return null;
    }
}
