package com.latashinsky.java.topics.repositories.json;

import com.latashinsky.java.topics.entities.json.JsonUser;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashSet;
import java.util.UUID;

public class JsonUserRepository implements UserRepository<JsonUser> {
    private static final JsonUserRepository jsonUserRepository = new JsonUserRepository();
    private static final Logger logger = LoggerFactory.getLogger(JsonUserRepository.class);

    private JsonUserRepository() {
    }

    public static JsonUserRepository getInstance() {
        return jsonUserRepository;
    }

    @Override
    public JsonUser findById(UUID id) {
        return findAll().stream().filter(r -> r.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public HashSet<JsonUser> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH_TO_DATA)) {
            return new HashSet<>();
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH_TO_DATA + "users.json"))) {
            String json = fileReader.readLine();
            if (json == null) {
                return new HashSet<>();
            }
            return new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
            return null;
        }
        return new HashSet<>();
    }

    @Override
    public void save(JsonUser user) {
        HashSet<JsonUser> hashSet = findAll();
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        } else {
            hashSet.remove(user);
        }
        String str = "[]";
        hashSet.add(user);
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "users.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void delete(JsonUser user) {
        if (user == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonUser> hashSet = findAll();
            hashSet.removeIf(r -> user.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "users.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
