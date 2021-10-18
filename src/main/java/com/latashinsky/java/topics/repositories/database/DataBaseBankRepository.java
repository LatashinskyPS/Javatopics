package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.database.DataBaseBank;
import com.latashinsky.java.topics.repositories.BankRepository;
import com.latashinsky.java.topics.repositories.MyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseBankRepository implements BankRepository<DataBaseBank> {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static MyRepository<DataBaseBank> instance;

    private DataBaseBankRepository() {

    }

    public static MyRepository<DataBaseBank> getInstance() {
        if (instance == null) {
            instance = new DataBaseBankRepository();
        }
        return instance;
    }

    @Override
    public DataBaseBank findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseBank bank = session.get(DataBaseBank.class, id);
        session.getTransaction().commit();
        session.close();
        return bank;
    }

    @Override
    public List<DataBaseBank> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseBank> banks = session.createQuery("FROM DataBaseBank ").list();
        session.getTransaction().commit();
        session.close();
        return banks;
    }

    @Override
    public void save(DataBaseBank bank) {
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
    public void delete(DataBaseBank bank) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(bank);
        session.getTransaction().commit();
        session.close();
    }
}
