package com.latashinsky.java.topics.repositories.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.json.JsonCurrency;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.CurrencyRepository;

import java.io.*;
import java.util.HashSet;

public class JsonCurrencyRepository implements CurrencyRepository<JsonCurrency> {

    private static final JsonCurrencyRepository jsonCurrencyRepository = new JsonCurrencyRepository();

    private JsonCurrencyRepository() {
    }

    public static JsonCurrencyRepository getInstance() {
        return jsonCurrencyRepository;
    }


    @Override
    public Currency findByName(String name) {
        return findAll().stream().filter(r -> r.getName().equals(name)).findAny().orElse(null);
    }

    @Override
    public JsonCurrency findById(int id) {
        return findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonCurrency> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH)) {
            return new HashSet<>();
        }
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH + "currencies.json"))) {
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
        HashSet<JsonCurrency> hashSet = null;
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<JsonCurrency>>() {
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
    public void save(JsonCurrency jsonCurrency) {
        String str = "[]";
        HashSet<JsonCurrency> hashSet = findAll();
        int idMax = findAll().stream().map(JsonCurrency::getId).max(Integer::compare).orElse(0);
        if (jsonCurrency.getId() == 0) {
            jsonCurrency.setId(idMax + 1);
            hashSet.add(jsonCurrency);
        }
        try {
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

    @Override
    public void delete(JsonCurrency jsonCurrency) {
        if (jsonCurrency == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonCurrency> hashSet = findAll();
            hashSet.removeIf(r -> jsonCurrency.getId() == r.getId());
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
