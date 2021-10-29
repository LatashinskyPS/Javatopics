package com.latashinsky.java.topics;

import org.hibernate.SessionFactory;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    public static void setSessionFactory(SessionFactory sessionFactorySpring) {
        sessionFactory = sessionFactorySpring;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
