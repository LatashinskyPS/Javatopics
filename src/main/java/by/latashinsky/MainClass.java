package by.latashinsky;

import by.latashinsky.user.interfaces.Application;
import org.hibernate.SessionFactory;

public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    public static void main(String... args) {
        Application.run();
    }
}
