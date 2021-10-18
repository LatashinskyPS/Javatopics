package com.latashinsky.java.topics.helpers;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.Currency;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class DataBaseCurrencyExchangeRateHelper implements CurrencyExchangeRateHelper {
    @Override
    @SuppressWarnings("unchecked")
    public HashMap<String, BigDecimal> getCurrencyExchangeRate() {
        HashMap<String, BigDecimal> hashMap = new HashMap<>();
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ((List<Currency>) session.createQuery("from DataBaseCurrency ").list())
                .forEach(r -> hashMap.put(r.getName(), r.getValue()));
        session.getTransaction().commit();
        session.close();
        return hashMap;
    }
}
