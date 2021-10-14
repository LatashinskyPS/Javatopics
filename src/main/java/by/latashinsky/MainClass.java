package by.latashinsky;

import by.latashinsky.user.interfaces.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.SessionFactory;

public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    public static void main(String... args) throws JsonProcessingException {
        Application.run();
    }
}

