package by.latashinsky.java.topics;

import by.latashinsky.java.topics.entities.database.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getNewSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory getNewSessionFactory() {
        String rootPath = Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "MyProperties.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Configuration()
                .setProperty("hibernate.connection.username",
                        properties.getProperty("database_username"))
                .setProperty("hibernate.connection.password",
                        properties.getProperty("database_password"))
                .setProperty("hibernate.connection.url",
                        properties.getProperty("database_url"))
                .addAnnotatedClass(DataBaseBank.class).addAnnotatedClass(DataBaseAccount.class)
                .addAnnotatedClass(DataBaseUser.class).addAnnotatedClass(DataBaseCurrency.class)
                .addAnnotatedClass(DataBaseTransaction.class)
                .buildSessionFactory();
    }
}
