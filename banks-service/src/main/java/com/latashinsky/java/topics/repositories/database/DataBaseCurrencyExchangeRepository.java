package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;
import com.latashinsky.java.topics.entities.database.DataBaseBank;
import com.latashinsky.java.topics.entities.database.DataBaseCurrencyExchange;
import com.latashinsky.java.topics.repositories.CurrencyExchangeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataBaseCurrencyExchangeRepository implements CurrencyExchangeRepository<DataBaseCurrencyExchange> {

    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static DataBaseCurrencyExchangeRepository instance;

    private DataBaseCurrencyExchangeRepository() {

    }

    public static DataBaseCurrencyExchangeRepository getInstance() {
        if (instance == null) {
            instance = new DataBaseCurrencyExchangeRepository();
        }
        return instance;
    }

    @Override
    public DataBaseCurrencyExchange findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseCurrencyExchange dataBaseCurrencyExchange = session.get(DataBaseCurrencyExchange.class, id);
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrencyExchange;
    }

    @Override
    public Collection<DataBaseCurrencyExchange> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseCurrencyExchange> dataBaseCurrencyExchange = session.createQuery("FROM DataBaseCurrencyExchange ").list();
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrencyExchange;
    }

    @Override
    public void save(DataBaseCurrencyExchange dataBaseCurrencyExchange) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (dataBaseCurrencyExchange.getId() != null) {
            session.update(dataBaseCurrencyExchange);
        } else {
            session.save(dataBaseCurrencyExchange);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(DataBaseCurrencyExchange dataBaseCurrencyExchange) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.find(DataBaseCurrencyExchange.class, dataBaseCurrencyExchange.getId()));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public CurrencyExchange findByCurrencyWhereDateIsNow(Currency<?> currency) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseCurrencyExchange dataBaseCurrencyExchange = (DataBaseCurrencyExchange) session
                .createQuery("from DataBaseCurrencyExchange a where a.currencyId = :currencyId and a.date = :date")
                .setParameter("currencyId", currency.getId())
                .setParameter("date", new Date())
                .getResultStream().findAny().orElse(null);
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrencyExchange;
    }

    @Override
    public Collection<CurrencyExchange> findByCurrency(Currency<?> currency) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<CurrencyExchange> dataBaseCurrencyExchange = (List<CurrencyExchange>) session
                .createQuery("from DataBaseCurrencyExchange a where a.currencyId = :currencyId")
                .setParameter("currencyId", currency.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return dataBaseCurrencyExchange;
    }
}
