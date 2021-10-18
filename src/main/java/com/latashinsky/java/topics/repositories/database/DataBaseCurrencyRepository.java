package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.database.DataBaseCurrency;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import com.latashinsky.java.topics.repositories.MyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;

public class DataBaseCurrencyRepository implements CurrencyRepository<DataBaseCurrency> {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static MyRepository<DataBaseCurrency> instance;

    private DataBaseCurrencyRepository() {

    }

    public static MyRepository<DataBaseCurrency> getInstance() {
        if (instance == null) {
            instance = new DataBaseCurrencyRepository();
        }
        return instance;
    }

    @Override
    public DataBaseCurrency findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseCurrency dataBaseCurrency = session.get(DataBaseCurrency.class, id);
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrency;
    }

    @Override
    public Collection<DataBaseCurrency> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseCurrency> dataBaseCurrency = session.createQuery("FROM DataBaseCurrency ").list();
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrency;
    }

    @Override
    public void save(DataBaseCurrency dataBaseCurrency) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (dataBaseCurrency.getId() != 0) {
            session.update(dataBaseCurrency);
        } else {
            session.save(dataBaseCurrency);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(DataBaseCurrency dataBaseCurrency) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(dataBaseCurrency);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Currency findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseCurrency dataBaseCurrency = (DataBaseCurrency) session
                .createQuery("from DataBaseCurrency a where a.name= :name")
                .setParameter("name", name)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrency;
    }
}
