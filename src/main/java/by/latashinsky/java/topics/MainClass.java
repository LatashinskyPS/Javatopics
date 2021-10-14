package by.latashinsky.java.topics;

import by.latashinsky.java.topics.interfaces.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
    private static final Logger logger = LoggerFactory.getLogger(MainClass.class);
    public static void main(String... args) {
        SpringApplication.run(MainClass.class);
        Application.run();
    }
}

