package by.latashinsky.java.topics.repositories;

import by.latashinsky.java.topics.HibernateSessionFactory;
import by.latashinsky.java.topics.entities.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseAccountRepository implements MyRepository<Account> {

    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    private static DataBaseAccountRepository accountRepository;

    private DataBaseAccountRepository() {

    }

    public static MyRepository<Account> getInstance() {
        if (accountRepository == null) {
            accountRepository = new DataBaseAccountRepository();
        }
        return accountRepository;
    }

    @Override
    public Account findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Account account = session.get(Account.class, id);
        session.getTransaction().commit();
        session.close();
        return account;
    }

    @Override
    public List<Account> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Account> accounts = session.createQuery("FROM Account").list();
        session.getTransaction().commit();
        session.close();
        return accounts;
    }

    @Override
    public void save(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (account.getId() != 0) {
            session.update(account);
        } else {
            session.save(account);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(account);
        session.getTransaction().commit();
        session.close();
    }
}
