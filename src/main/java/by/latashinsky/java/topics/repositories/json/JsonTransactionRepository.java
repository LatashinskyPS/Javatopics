package by.latashinsky.java.topics.repositories.json;

import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.json.JsonTransaction;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashSet;

public class JsonTransactionRepository implements MyRepository<JsonTransaction> {
    private static final JsonTransactionRepository jsonTransactionRepository = new JsonTransactionRepository();

    private JsonTransactionRepository() {
    }

    private JsonTransaction update(JsonTransaction transaction) {
        if (transaction == null) return null;
        JsonAccountRepository jsonAccountRepository = JsonAccountRepository.getInstance();
        transaction.setAccountFrom(jsonAccountRepository.findById(transaction.getIdAccountFrom()));
        transaction.setAccountTo(jsonAccountRepository.findById(transaction.getIdAccountTo()));
        return transaction;
    }

    public static JsonTransactionRepository getInstance() {
        return jsonTransactionRepository;
    }

    @Override
    public JsonTransaction findById(int id) {
        return update(
                findAll().stream().filter(r -> r.getId() == id)
                        .findAny().orElse(null));
    }

    @Override
    public HashSet<JsonTransaction> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/transactions.json"))) {
            String json = fileReader.readLine();
            if (json == null) return new HashSet<>();
            HashSet<JsonTransaction> hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<JsonTransaction>>() {
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
    public void save(JsonTransaction transaction) {
        if (transaction.getAccountFrom() == null || transaction.getAccountTo() == null) return;
        transaction.setIdAccountTo(transaction.getAccountTo().getId());
        transaction.setIdAccountFrom(transaction.getAccountFrom().getId());
        String str;
        int idMax = findAll().stream().map(Transaction::getId).max(Integer::compare).orElse(0);
        HashSet<JsonTransaction> hashSet = findAll();
        if (transaction.getId() == 0) {
            transaction.setId(1 + idMax);
        } else {
            hashSet.remove(transaction);
        }
        hashSet.add(transaction);
        try (FileWriter fileWriter = new FileWriter("data/transactions.json")) {
            str = new ObjectMapper().writeValueAsString(hashSet);
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(JsonTransaction transaction) {
        if (transaction == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonTransaction> hashSet = findAll();
            hashSet.removeIf(r -> transaction.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("data/transactions.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

