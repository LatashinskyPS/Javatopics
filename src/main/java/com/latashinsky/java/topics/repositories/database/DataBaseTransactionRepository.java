package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.database.DataBaseTransaction;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class DataBaseTransactionRepository implements TransactionRepository<DataBaseTransaction> {
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
    public DataBaseTransaction findById(UUID id) {
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
        if (transaction.getId() != null) {
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

    @Override
    public Collection<DataBaseTransaction> getTransactionsUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseTransaction> dataBaseAccounts = session
                .createQuery("SELECT distinct t "
                        + "from DataBaseUser u join DataBaseAccount a on u.id =a.userId  "
                        + "join DataBaseTransaction t on a.id = t.accountFromId or a.id = t.accountToId "
                        + "where u.id =:idUser")
                .setParameter("idUser", user.getId())
                .list();
        session.getTransaction().commit();
        session.close();
        return dataBaseAccounts;
    }
}
