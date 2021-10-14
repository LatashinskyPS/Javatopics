package by.latashinsky.java.topics.repositories;

import by.latashinsky.java.topics.HibernateSessionFactory;
import by.latashinsky.java.topics.entities.Bank;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseBankRepository implements MyRepository<Bank> {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static MyRepository<Bank> instance;

    private DataBaseBankRepository() {

    }

    public static MyRepository<Bank> getInstance() {
        if (instance == null) {
            instance = new DataBaseBankRepository();
        }
        return instance;
    }

    @Override
    public Bank findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Bank bank = session.get(Bank.class, id);
        session.getTransaction().commit();
        session.close();
        return bank;
    }

    @Override
    public List<Bank> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Bank> banks = session.createQuery("FROM Bank ").list();
        session.getTransaction().commit();
        session.close();
        return banks;
    }

    @Override
    public void save(Bank bank) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (bank.getId() != 0) {
            session.update(bank);
        } else {
            session.save(bank);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Bank bank) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(bank);
        session.getTransaction().commit();
        session.close();
    }
}
