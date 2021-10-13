package by.latashinsky.factory;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.entities.Transaction;
import by.latashinsky.entities.User;
import by.latashinsky.repositories.*;

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
