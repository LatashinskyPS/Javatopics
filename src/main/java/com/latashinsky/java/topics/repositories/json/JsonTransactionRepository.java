package com.latashinsky.java.topics.repositories.json;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.json.JsonTransaction;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonTransactionRepository implements TransactionRepository<JsonTransaction> {
    private static final JsonTransactionRepository jsonTransactionRepository = new JsonTransactionRepository();

    private JsonTransactionRepository() {
    }

    private JsonTransaction update(JsonTransaction transaction) {
        if (transaction == null) {
            return null;
        }
        JsonAccountRepository jsonAccountRepository = JsonAccountRepository.getInstance();
        transaction.setAccountFrom(jsonAccountRepository.findById(transaction.getAccountFromId()));
        transaction.setAccountTo(jsonAccountRepository.findById(transaction.getAccountToId()));
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
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH)) {
            return new HashSet<>();
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader("transactions.json"))) {
            String json = fileReader.readLine();
            if (json == null) {
                return new HashSet<>();
            }
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
        if (transaction.getAccountFrom() == null || transaction.getAccountTo() == null) {
            return;
        }
        transaction.setAccountToId(transaction.getAccountTo().getId());
        transaction.setAccountFromId(transaction.getAccountFrom().getId());
        String str;
        int idMax = findAll().stream().map(Transaction::getId).max(Integer::compare).orElse(0);
        HashSet<JsonTransaction> hashSet = findAll();
        if (transaction.getId() == 0) {
            transaction.setId(1 + idMax);
        } else {
            hashSet.remove(transaction);
        }
        hashSet.add(transaction);
        try (FileWriter fileWriter = new FileWriter("transactions.json")) {
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
        try (FileWriter fileWriter = new FileWriter("transactions.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<JsonTransaction> getTransactionsUser(User user) {
        List<JsonTransaction> transactionList = new LinkedList<>();
        MyRepository<Account> repositoryAccount = Factory.getInstance().getRepository(Account.class);
        repositoryAccount.findAll().stream().filter(r -> r.getUserId() == user.getId())
                .forEach(r -> transactionList.addAll(
                        findAll().stream().filter(a -> a.getAccountToId() == user.getId()
                                || a.getAccountFromId() == user.getId()).collect(Collectors.toList())));
        return transactionList;
    }
}

