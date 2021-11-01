package com.latashinsky.banksservice.repositories.database;

import com.latashinsky.banksservice.HibernateSessionFactory;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.entities.database.DataBaseAccount;
import com.latashinsky.banksservice.repositories.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DataBaseAccountRepository implements AccountRepository<DataBaseAccount> {

    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    private static DataBaseAccountRepository accountRepository;

    private DataBaseAccountRepository() {

    }

    public static DataBaseAccountRepository getInstance() {
        if (accountRepository == null) {
            accountRepository = new DataBaseAccountRepository();
        }
        return accountRepository;
    }

    @Override
    public DataBaseAccount findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseAccount account = session.get(DataBaseAccount.class, id);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    @Override
    public List<DataBaseAccount> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseAccount> dataBaseAccounts = session.createQuery("FROM DataBaseAccount ").list();
        session.getTransaction().commit();
        session.close();
        return dataBaseAccounts;
    }

    @Override
    public void save(DataBaseAccount dataBaseAccount) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (dataBaseAccount.getId() != null) {
            session.update(dataBaseAccount);
        } else {
            session.save(dataBaseAccount);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(DataBaseAccount dataBaseAccount) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(DataBaseAccount.class, dataBaseAccount.getId()));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection<DataBaseAccount> getAccountsBank(Bank<?> bank) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseAccount> dataBaseAccounts = session
                .createQuery("from DataBaseAccount a where a.bankId= :idBank")
                .setParameter("idBank", bank.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return dataBaseAccounts;
    }

    @Override
    public Collection<DataBaseAccount> getAccountsUser(User<?> user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseAccount> dataBaseAccounts = session
                .createQuery("from DataBaseAccount a where a.userId= :idUser")
                .setParameter("idUser", user.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return dataBaseAccounts;
    }
}
