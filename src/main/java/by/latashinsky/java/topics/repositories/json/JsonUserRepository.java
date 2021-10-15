package by.latashinsky.java.topics.repositories.json;

import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.entities.json.JsonUser;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class JsonUserRepository implements MyRepository<JsonUser> {
    private static final JsonUserRepository jsonUserRepository = new JsonUserRepository();

    private JsonUserRepository() {
    }

    public JsonUser update(JsonUser user) {
        if (user == null) return null;
        user.setAccounts(new ArrayList<>());
        return user;
    }

    public static JsonUserRepository getInstance() {
        return jsonUserRepository;
    }

    @Override
    public JsonUser findById(int id) {
        return update(findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null));
    }

    @Override
    public HashSet<JsonUser> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/users.json"))) {
            String json = fileReader.readLine();
            if (json == null) return new HashSet<>();
            HashSet<JsonUser> hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<JsonUser>>() {
            });
            hashSet.forEach(this::update);
            return hashSet;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new HashSet<>();
    }

    @Override
    public void save(JsonUser user) {
        HashSet<JsonUser> hashSet = findAll();
        int idMax = findAll().stream().map(User::getId).max(Integer::compare).orElse(0);
        if (user.getId() == 0) {
            user.setId(idMax + 1);
        } else {
            hashSet.remove(user);
        }
        String str = "[]" ;
        hashSet.add(user);
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("data/users.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(JsonUser user) {
        if (user == null) {
            return;
        }
        String str = "[]" ;
        try {
            HashSet<JsonUser> hashSet = findAll();
            hashSet.removeIf(r -> user.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("data/users.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
