package com.latashinsky.java.topics.repositories.database;

import com.latashinsky.java.topics.HibernateSessionFactory;
import com.latashinsky.java.topics.entities.database.DataBaseUser;
import com.latashinsky.java.topics.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.UUID;

public class DataBaseUserRepository implements UserRepository<DataBaseUser> {
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    private static DataBaseUserRepository userRepository;

    private DataBaseUserRepository() {
    }

    public static DataBaseUserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new DataBaseUserRepository();
        }
        return userRepository;
    }

    @Override
    public DataBaseUser findById(UUID id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        DataBaseUser user = session.get(DataBaseUser.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<DataBaseUser> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<DataBaseUser> users = session.createQuery("FROM DataBaseUser").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void save(DataBaseUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (user.getId() != null) {
            session.update(user);
        } else {
            session.save(user);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(DataBaseUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(session.find(DataBaseUser.class, user.getId()));
        session.getTransaction().commit();
        session.close();
    }
}
