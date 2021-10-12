package by.latashinsky.repositories;

import by.latashinsky.HibernateSessionFactory;
import by.latashinsky.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DataBaseUserRepository implements MyRepository<User> {
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
    public User findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        User user = session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("FROM User ").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if (user.getId() != 0) {
            session.update(user);
        } else {
            session.save(user);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}
