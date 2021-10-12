package by.latashinsky.factory;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.entities.User;
import by.latashinsky.repositories.AccountRepository;
import by.latashinsky.repositories.BankRepository;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.repositories.UserRepository;

public class RepositoryFactory {
    public <T> MyRepository<?> getRepository(Class<T> clazz) {
        if (clazz.equals(Bank.class)) {
            return BankRepository.getInstance();
        }
        if (clazz.equals(Account.class)) {
            return AccountRepository.getInstance();
        }
        if (clazz.equals(User.class)) {
            return UserRepository.getInstance();
        }
        return null;
    }
}
