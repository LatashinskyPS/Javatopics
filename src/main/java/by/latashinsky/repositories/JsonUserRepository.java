package by.latashinsky.repositories;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.User;
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
        List<Account> accounts = user.getAccounts();
        user.setAccounts(new ArrayList<>());
        JsonAccountRepository.getInstance().findAll().forEach(
                r -> {
                    if (r.getIdBank() == user.getId()) {
                        accounts.add(r);
                    }
                }
        );
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
        try (BufferedReader fileReader = new BufferedReader(new FileReader("users.json"))) {
            String str = fileReader.readLine();
            HashSet<User> hashSet = new ObjectMapper().readValue(str, new TypeReference<HashSet<User>>() {
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
        try (FileWriter fileWriter = new FileWriter("users.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(user)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (FileWriter fileWriter = new FileWriter("users.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().removeIf(r -> user.getId() == r.getId())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
