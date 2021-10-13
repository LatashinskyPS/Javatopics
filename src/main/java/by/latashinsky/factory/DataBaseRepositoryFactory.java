package by.latashinsky.factory;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.entities.Transaction;
import by.latashinsky.entities.User;
import by.latashinsky.repositories.*;

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
