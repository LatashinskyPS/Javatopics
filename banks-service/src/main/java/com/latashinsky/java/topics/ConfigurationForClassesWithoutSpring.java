package com.latashinsky.java.topics;

import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.services.MessageService;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@Component
public class ConfigurationForClassesWithoutSpring implements ApplicationContextAware {
    ApplicationContext applicationContext;
    private final Environment env;

    public ConfigurationForClassesWithoutSpring(Environment env) {
        this.env = env;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void postConstruct() {
        Constants.PATH = env.getProperty("path.data.json");
        Constants.PATH_TO_DATA = Constants.PATH + "data/";
        Constants.PROFILE = env.getProperty("profile");
        Constants.messageService = applicationContext.getBean(MessageService.class);
        SessionFactory sessionFactory = applicationContext.getBean(EntityManagerFactory.class).unwrap(SessionFactory.class);
        HibernateSessionFactory.setSessionFactory(sessionFactory);
    }
}
