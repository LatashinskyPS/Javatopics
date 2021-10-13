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
        try (BufferedReader fileReader = new BufferedReader(new FileReader("transactions.json"))) {
            String str = fileReader.readLine();
            HashSet<Transaction> hashSet = new ObjectMapper().readValue(str, new TypeReference<HashSet<Transaction>>() {
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
        try (FileWriter fileWriter = new FileWriter("transactions.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(transaction)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Transaction transaction) {
        try (FileWriter fileWriter = new FileWriter("transactions.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().removeIf(r -> transaction.getId() == r.getId())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
