package by.latashinsky.java.topics;

import by.latashinsky.java.topics.interfaces.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.SessionFactory;

public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    public static void main(String... args) {
        Application.run();
    }
}

