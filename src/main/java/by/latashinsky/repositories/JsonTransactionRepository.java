package by.latashinsky.repositories;

import by.latashinsky.entities.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashSet;

public class JsonTransactionRepository implements MyRepository<Transaction> {
    private static final JsonTransactionRepository jsonTransactionRepository = new JsonTransactionRepository();

    private JsonTransactionRepository() {
    }

    private Transaction update(Transaction transaction) {
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
    public Transaction findById(int id) {
        return update(
                findAll().stream().filter(r -> r.getId() == id)
                        .findAny().orElse(null));
    }

    @Override
    public HashSet<Transaction> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/transactions.json"))) {
            String json = fileReader.readLine();
            if (json == null) return new HashSet<>();
            HashSet<Transaction> hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<Transaction>>() {
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
    public void save(Transaction transaction) {
        if (transaction.getAccountFrom() == null || transaction.getAccountTo() == null) return;
        transaction.setIdAccountTo(transaction.getAccountTo().getId());
        transaction.setIdAccountFrom(transaction.getAccountFrom().getId());
        String str = "empty";
        int idMax = findAll().stream().map(Transaction::getId).max(Integer::compare).orElse(0);
        HashSet<Transaction> hashSet = findAll();
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
    public void delete(Transaction transaction) {
        if (transaction == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<Transaction> hashSet = findAll();
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

