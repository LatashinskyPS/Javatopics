package by.latashinsky.factory;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.entities.User;
import by.latashinsky.repositories.DataBaseAccountRepository;
import by.latashinsky.repositories.DataBaseBankRepository;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.repositories.DataBaseUserRepository;

public class RepositoryFactory {
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
        return null;
    }
}
