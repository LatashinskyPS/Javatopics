package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.database.DataBaseAccount;
import com.latashinsky.java.topics.repositories.AccountRepository;
import com.latashinsky.java.topics.repositories.MyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

public class DataBaseAccountRepository implements AccountRepository<DataBaseAccount> {

    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    private static DataBaseAccountRepository accountRepository;

    private DataBaseAccountRepository() {

    }

    public static MyRepository<DataBaseAccount> getInstance() {
        if (accountRepository == null) {
            accountRepository = new DataBaseAccountRepository();
        }
        return accountRepository;
    }

    @Override
    public DataBaseAccount findById(int id) {
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
        if (dataBaseAccount.getId() != 0) {
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
        session.delete(dataBaseAccount);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Collection<DataBaseAccount> getAccountsBank(Bank bank) {
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
    public Collection<DataBaseAccount> getAccountsUser(User user) {
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
