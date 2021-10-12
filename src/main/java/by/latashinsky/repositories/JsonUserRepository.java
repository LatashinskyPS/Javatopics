package by.latashinsky.repositories;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class JsonUserRepository implements MyRepository<User>{
    private static final JsonUserRepository jsonUserRepository = new JsonUserRepository();

    private JsonUserRepository() {
    }

    public static JsonUserRepository getInstance() {
        return jsonUserRepository;
    }
    @Override
    public User findById(int id) {
        return findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
    }

    @Override
    public HashSet<User> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("accounts.json"))) {
            String str = fileReader.readLine();
            return new ObjectMapper().readValue(str, new TypeReference<HashSet<User>>() {
            });
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

    }

    @Override
    public void delete(User user) {

    }
}
