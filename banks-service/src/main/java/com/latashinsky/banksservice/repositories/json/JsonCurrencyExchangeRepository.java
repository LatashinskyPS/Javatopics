package com.latashinsky.banksservice.repositories.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.CurrencyExchange;
import com.latashinsky.banksservice.entities.json.JsonCurrencyExchange;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.helpers.DirectoryHelper;
import com.latashinsky.banksservice.repositories.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class JsonCurrencyExchangeRepository implements CurrencyExchangeRepository<JsonCurrencyExchange> {
    private static final Logger logger = LoggerFactory.getLogger(JsonCurrencyExchangeRepository.class);
    private static final JsonCurrencyExchangeRepository jsonCurrencyExchangeRepository = new JsonCurrencyExchangeRepository();

    private JsonCurrencyExchangeRepository() {
    }

    public static JsonCurrencyExchangeRepository getInstance() {
        return jsonCurrencyExchangeRepository;
    }

    @Override
    public CurrencyExchange findByCurrencyWhereDateIsNow(Currency<?> currency) {
        Date date = new Date();
        return findAll().stream().filter(r -> r.getCurrencyId() == currency.getId() && r.getDate().equals(date)).findAny().orElse(null);
    }

    @Override
    public Collection<CurrencyExchange> findByCurrency(Currency<?> currency) {
        return findAll().stream().filter(r -> r.getCurrencyId() == currency.getId()).collect(Collectors.toSet());
    }

    @Override
    public JsonCurrencyExchange findById(UUID id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonCurrencyExchange> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH_TO_DATA)) {
            return new HashSet<>();
        }
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH_TO_DATA + "currencyExchanges.json"))) {
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
        HashSet<JsonCurrencyExchange> hashSet = null;
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
    public void save(JsonCurrencyExchange jsonCurrencyExchange) {
        String str = "[]";
        HashSet<JsonCurrencyExchange> hashSet = findAll();
        try {
            if (jsonCurrencyExchange.getId() == null) {
                jsonCurrencyExchange.setId(UUID.randomUUID());
                hashSet.add(jsonCurrencyExchange);
            }
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "currencyExchanges.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void delete(JsonCurrencyExchange jsonCurrencyExchange) {
        if (jsonCurrencyExchange == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonCurrencyExchange> hashSet = findAll();
            hashSet.removeIf(r -> jsonCurrencyExchange.getId() == r.getId());
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
