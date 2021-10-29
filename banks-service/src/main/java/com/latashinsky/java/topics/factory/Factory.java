package com.latashinsky.java.topics.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.factories.DataBaseEntitiesFactory;
import com.latashinsky.java.topics.entities.factories.EntitiesFactory;
import com.latashinsky.java.topics.entities.factories.JsonEntitiesFactory;
import com.latashinsky.java.topics.exceptions.FactoryConfigurationException;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.repositories.factories.DataBaseRepositoryFactory;
import com.latashinsky.java.topics.repositories.factories.JsonRepositoryFactory;
import com.latashinsky.java.topics.repositories.factories.RepositoryFactory;

public class Factory {
    private Factory() {
        configure();
    }

    private RepositoryFactory repositoryFactory;
    private static Factory instance;
    private EntitiesFactory entitiesFactory;

    public <T> T getRepository(TypeReference<T> typeReference) {
        return repositoryFactory.getRepository(typeReference);
    }

    public <T> T getEntity(TypeReference<T> typeReference) {
        return entitiesFactory.getEntity(typeReference);
    }

    public static Factory getInstance() {
        if (instance == null) {
            instance = new Factory();
        }
        return instance;
    }

    private void configure() {
        String profile = Constants.PROFILE;
        switch (profile) {
            case "DB": {
                repositoryFactory = new DataBaseRepositoryFactory();
                entitiesFactory = new DataBaseEntitiesFactory();
                break;
            }
            case "JSON": {
                repositoryFactory = new JsonRepositoryFactory();
                entitiesFactory = new JsonEntitiesFactory();
                break;
            }
            default: {
                throw new FactoryConfigurationException();
            }
        }
    }
}
