package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.database.DataBaseBank;
import com.latashinsky.java.topics.entities.database.DataBaseCurrency;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DataBaseCurrencyRepository implements CurrencyRepository<DataBaseCurrency> {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static DataBaseCurrencyRepository instance;

    private DataBaseCurrencyRepository() {

    }

    public static DataBaseCurrencyRepository getInstance() {
        if (instance == null) {
            instance = new DataBaseCurrencyRepository();
        }
        return instance;
    }

    @Override
    public DataBaseCurrency findById(UUID id) {
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
        if (dataBaseCurrency.getId() != null) {
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
        session.delete(session.find(DataBaseCurrency.class, dataBaseCurrency.getId()));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Currency<?> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        DataBaseCurrency dataBaseCurrency = (DataBaseCurrency) session
                .createQuery("from DataBaseCurrency a where a.name= :name")
                .setParameter("name", name)
                .getResultStream().findAny().orElse(null);
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrency;
    }
}
