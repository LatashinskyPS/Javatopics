package com.latashinsky.java.topics.repositories.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;
import com.latashinsky.java.topics.entities.json.JsonCurrency;
import com.latashinsky.java.topics.entities.json.JsonCurrencyExchange;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.CurrencyExchangeRepository;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public class JsonCurrencyExchangeRepository implements CurrencyExchangeRepository<JsonCurrencyExchange> {
    private static final JsonCurrencyExchangeRepository jsonCurrencyExchangeRepository = new JsonCurrencyExchangeRepository();

    private JsonCurrencyExchangeRepository() {
    }

    public static JsonCurrencyExchangeRepository getInstance() {
        return jsonCurrencyExchangeRepository;
    }

    @Override
    public CurrencyExchange findByCurrencyWhereDateIsNow(Currency currency) {
        Date date = new Date();
        return findAll().stream().filter(r -> r.getCurrencyId() == currency.getId() && r.getDate().equals(date)).findAny().orElse(null);
    }

    @Override
    public Collection<CurrencyExchange> findByCurrency(Currency currency) {
        return findAll().stream().filter(r -> r.getCurrencyId() == currency.getId()).collect(Collectors.toSet());
    }

    @Override
    public JsonCurrencyExchange findById(UUID id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonCurrencyExchange> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH)) {
            return new HashSet<>();
        }
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH + "currencyExchanges.json"))) {
            json = fileReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (json == null) {
            return new HashSet<>();
        }
        HashSet<JsonCurrencyExchange> hashSet = null;
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<JsonCurrencyExchange>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH + "currencyExchanges.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH + "currencies.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
