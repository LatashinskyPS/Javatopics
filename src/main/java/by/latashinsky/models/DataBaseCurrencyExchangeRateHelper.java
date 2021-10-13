package by.latashinsky.models;

import by.latashinsky.HibernateSessionFactory;
import by.latashinsky.entities.Currency;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class DataBaseCurrencyExchangeRateHelper implements CurrencyExchangeRateHelper {
    @Override
    public HashMap<String, BigDecimal> getCurrencyExchangeRate() {
        HashMap<String, BigDecimal> hashMap = new HashMap<>();
        Session session = HibernateSessionFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ((List<Currency>) session.createQuery("from Currency").list()).
                forEach(r -> hashMap.put(r.getName(), r.getValue()));
        session.getTransaction().commit();
        session.close();
        return hashMap;
    }
}
