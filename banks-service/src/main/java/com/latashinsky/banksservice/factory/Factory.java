package com.latashinsky.banksservice.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.factories.DataBaseEntitiesFactory;
import com.latashinsky.banksservice.entities.factories.EntitiesFactory;
import com.latashinsky.banksservice.entities.factories.JsonEntitiesFactory;
import com.latashinsky.banksservice.exceptions.FactoryConfigurationException;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.repositories.factories.DataBaseRepositoryFactory;
import com.latashinsky.banksservice.repositories.factories.JsonRepositoryFactory;
import com.latashinsky.banksservice.repositories.factories.RepositoryFactory;

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
