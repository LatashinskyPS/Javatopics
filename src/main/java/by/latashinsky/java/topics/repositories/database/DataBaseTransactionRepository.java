package by.latashinsky.java.topics.repositories.database;

import by.latashinsky.java.topics.HibernateSessionFactory;
import by.latashinsky.java.topics.entities.database.DataBaseTransaction;
import by.latashinsky.java.topics.repositories.MyRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseTransactionRepository implements MyRepository<DataBaseTransaction> {
    private static DataBaseTransactionRepository transactionRepository;

    private DataBaseTransactionRepository() {
    }

    public static DataBaseTransactionRepository getInstance() {
        if (transactionRepository == null) {
            transactionRepository = new DataBaseTransactionRepository();
        }
        return transactionRepository;
    }

    SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Override
    public DataBaseTransaction findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseTransaction transaction = session.get(DataBaseTransaction.class, id);
        session.getTransaction().commit();
        session.close();
        return transaction;
    }

    @Override
    public List<DataBaseTransaction> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseTransaction> users = session.createQuery("FROM DataBaseTransaction ").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void save(DataBaseTransaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (transaction.getId() != 0) {
            session.update(transaction);
        } else {
            session.save(transaction);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(DataBaseTransaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(transaction);
        session.getTransaction().commit();
        session.close();
    }
}
