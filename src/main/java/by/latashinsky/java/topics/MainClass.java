package by.latashinsky.java.topics;

import by.latashinsky.java.topics.interfaces.Application;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    public static void main(String... args) {
        SpringApplication.run(MainClass.class);
        Application.run();
    }
}

