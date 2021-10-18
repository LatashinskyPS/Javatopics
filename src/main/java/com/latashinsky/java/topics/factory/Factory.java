package com.latashinsky.java.topics.factory;

import com.latashinsky.java.topics.entities.factories.DataBaseEntitiesFactory;
import com.latashinsky.java.topics.entities.factories.EntitiesFactory;
import com.latashinsky.java.topics.entities.factories.JsonEntitiesFactory;
import com.latashinsky.java.topics.exceptions.FactoryConfigurationException;
import com.latashinsky.java.topics.helpers.CurrencyExchangeRateHelper;
import com.latashinsky.java.topics.helpers.DataBaseCurrencyExchangeRateHelper;
import com.latashinsky.java.topics.helpers.JsonCurrencyExchangeRateHelper;
import com.latashinsky.java.topics.repositories.factories.DataBaseRepositoryFactory;
import com.latashinsky.java.topics.repositories.factories.JsonRepositoryFactory;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.repositories.factories.RepositoryFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Factory {
    private Factory() {
        configure();
    }

    private RepositoryFactory repositoryFactory;
    private static Factory instance;
    private CurrencyExchangeRateHelper currencyExchangeRateHelper;
    private EntitiesFactory entitiesFactory;

    public <T> MyRepository<T> getRepository(Class<T> clazz) {
        return repositoryFactory.getRepository(clazz);
    }

    public <T> T getEntity(Class<T> clazz) {
        return entitiesFactory.getEntity(clazz);
    }

    public CurrencyExchangeRateHelper getCurrencyExchangeRateHelper() {
        return currencyExchangeRateHelper;
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    private void configure() {
        String rootPath = Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String appConfigPath = rootPath + "configuration.properties";
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String profile = properties.getProperty("profile");
        switch (profile) {
            case "DB": {
                repositoryFactory = new DataBaseRepositoryFactory();
                currencyExchangeRateHelper = new DataBaseCurrencyExchangeRateHelper();
                entitiesFactory = new DataBaseEntitiesFactory();
                break;
            }
            case "JSON": {
                repositoryFactory = new JsonRepositoryFactory();
                currencyExchangeRateHelper = new JsonCurrencyExchangeRateHelper();
                entitiesFactory = new JsonEntitiesFactory();
                break;
            }
            default: {
                throw new FactoryConfigurationException();
            }
        }
    }
}
