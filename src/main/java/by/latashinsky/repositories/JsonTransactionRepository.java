package by.latashinsky.repositories;

import by.latashinsky.entities.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashSet;

public class JsonTransactionRepository implements MyRepository<Transaction> {
    private static final JsonTransactionRepository jsonTransactionRepository = new JsonTransactionRepository();

    private JsonTransactionRepository() {
    }

    public static JsonTransactionRepository getInstance() {
        return jsonTransactionRepository;
    }
    @Override
    public Transaction findById(int id) {
        return findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
    }

    @Override
    public HashSet<Transaction> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("transactions.json"))) {
            String str = fileReader.readLine();
            return new ObjectMapper().readValue(str, new TypeReference<HashSet<Transaction>>() {
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
    public void save(Transaction transaction) {
        try (FileWriter fileWriter = new FileWriter("banks.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(transaction)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Transaction transaction) {

    }
}
