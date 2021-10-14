package by.latashinsky.java.topics.repositories;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JsonUserRepository implements MyRepository<User> {
    private static final JsonUserRepository jsonUserRepository = new JsonUserRepository();

    private JsonUserRepository() {
    }

    public User update(User user) {
        if (user == null) return null;
        user.setAccounts(new ArrayList<>());
        return user;
    }

    public static JsonUserRepository getInstance() {
        return jsonUserRepository;
    }

    @Override
    public User findById(int id) {
        return update(findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null));
    }

    @Override
    public HashSet<User> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/users.json"))) {
            String json = fileReader.readLine();
            if (json == null) return new HashSet<>();
            HashSet<User> hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<User>>() {
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
    public void save(User user) {
        HashSet<User> hashSet = findAll();
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
    public void delete(User user) {
        if (user == null) {
            return;
        }
        String str = "[]" ;
        try {
            HashSet<User> hashSet = findAll();
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
