package com.latashinsky.java.topics.repositories.json;

import com.latashinsky.java.topics.entities.json.JsonBank;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.BankRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;

public class JsonBankRepository implements BankRepository<JsonBank> {
    private static final JsonBankRepository jsonBankRepository = new JsonBankRepository();
    private static final Logger logger = LoggerFactory.getLogger(JsonBankRepository.class);

    private JsonBankRepository() {
    }

    public static JsonBankRepository getInstance() {
        return jsonBankRepository;
    }

    @Override
    public JsonBank findById(UUID id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonBank> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH_TO_DATA)) {
            return new HashSet<>();
        }
        HashSet<JsonBank> hashSet = null;
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH_TO_DATA + "banks.json"))) {
            json = fileReader.readLine();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        if (json == null) {
            return new HashSet<>();
        }
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
    public void save(JsonBank bank) {
        HashSet<JsonBank> hashSet = findAll();
        if (bank.getId() == null) {
            bank.setId(UUID.randomUUID());
        } else {
            hashSet.remove(bank);
        }
        hashSet.add(bank);
        String str = "empty";
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "banks.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void delete(JsonBank bank) {
        if (bank == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonBank> hashSet = findAll();
            hashSet.removeIf(r -> bank.getId().equals(r.getId()));
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "banks.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
