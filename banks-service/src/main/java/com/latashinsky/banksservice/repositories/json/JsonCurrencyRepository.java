package com.latashinsky.banksservice.repositories.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.json.JsonCurrency;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.helpers.DirectoryHelper;
import com.latashinsky.banksservice.repositories.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;

public class JsonCurrencyRepository implements CurrencyRepository<JsonCurrency> {
    private static final Logger logger = LoggerFactory.getLogger(JsonCurrencyRepository.class);

    private static final JsonCurrencyRepository jsonCurrencyRepository = new JsonCurrencyRepository();

    private JsonCurrencyRepository() {
    }

    public static JsonCurrencyRepository getInstance() {
        return jsonCurrencyRepository;
    }


    @Override
    public Currency<?> findByName(String name) {
        return findAll().stream().filter(r -> r.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public JsonCurrency findById(UUID id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonCurrency> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH_TO_DATA)) {
            return new HashSet<>();
        }
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH_TO_DATA + "currencies.json"))) {
            json = fileReader.readLine();
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
        if (json == null) {
            return new HashSet<>();
        }
        HashSet<JsonCurrency> hashSet = null;
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        if (hashSet == null) {
            return new HashSet<>();
        }
        return hashSet;
    }

    @Override
    public void save(JsonCurrency jsonCurrency) {
        String str = "[]";
        HashSet<JsonCurrency> hashSet = findAll();
        jsonCurrency.setId(UUID.randomUUID());
        hashSet.add(jsonCurrency);
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "currencies.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void delete(JsonCurrency jsonCurrency) {
        if (jsonCurrency == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonCurrency> hashSet = findAll();
            hashSet.removeIf(r -> jsonCurrency.getId().equals(r.getId()));
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "currencies.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
