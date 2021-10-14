package by.latashinsky.java.topics.repositories;

import by.latashinsky.java.topics.HibernateSessionFactory;
import by.latashinsky.java.topics.entities.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseTransactionRepository implements MyRepository<Transaction>{
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
    public Transaction findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Transaction transaction = session.get(Transaction.class,id);
        session.getTransaction().commit();
        session.close();
        return transaction;
    }

    public List<Transaction> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Transaction> users = session.createQuery("FROM Transaction ").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    public void save(Transaction transaction) {
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
    public void delete(Transaction transaction) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(transaction);
        session.getTransaction().commit();
        session.close();
    }
}
