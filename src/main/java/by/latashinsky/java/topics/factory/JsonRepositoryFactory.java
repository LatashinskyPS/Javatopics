package by.latashinsky.java.topics.factory;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.repositories.*;

public class JsonRepositoryFactory implements RepositoryFactory{
    @Override
    public <T> MyRepository<?> getRepository(Class<T> clazz) {
        if (clazz.equals(Bank.class)) {
            return JsonBankRepository.getInstance();
        }
        if (clazz.equals(Account.class)) {
            return JsonAccountRepository.getInstance();
        }
        if (clazz.equals(User.class)) {
            return JsonUserRepository.getInstance();
        }
        if (clazz.equals(Transaction.class)) {
            return JsonTransactionRepository.getInstance();
        }
        return null;
    }
}
