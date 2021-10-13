package by.latashinsky;

import by.latashinsky.entities.Bank;
import by.latashinsky.repositories.DataBaseBankRepository;
import by.latashinsky.repositories.JsonBankRepository;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.user.interfaces.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.hibernate.SessionFactory;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class MainClass {
    private static final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    public static void main(String... args) throws JsonProcessingException {
        Application.run();
    }
}

