package com.latashinsky.java.topics.repositories.json;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.json.JsonTransaction;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.DirectoryHelper;
import com.latashinsky.java.topics.repositories.AccountRepository;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class JsonTransactionRepository implements TransactionRepository<JsonTransaction> {
    private static final JsonTransactionRepository jsonTransactionRepository = new JsonTransactionRepository();
    private static final Logger logger = LoggerFactory.getLogger(JsonTransactionRepository.class);

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
    public JsonTransaction findById(UUID id) {
        return update(
                findAll().stream().filter(r -> r.getId().equals(id))
                        .findAny().orElse(null));
    }

    @Override
    public HashSet<JsonTransaction> findAll() {
        if (DirectoryHelper.mkdirIfNotExist(Constants.PATH_TO_DATA)) {
            return new HashSet<>();
        }
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH_TO_DATA + "transactions.json"))) {
            String json = fileReader.readLine();
            if (json == null) {
                return new HashSet<>();
            }
            HashSet<JsonTransaction> hashSet = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
            hashSet.forEach(this::update);
            return hashSet;
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        } catch (IOException e) {
            logger.info(e.getMessage());
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
        HashSet<JsonTransaction> hashSet = findAll();
        if (transaction.getId() == null) {
            transaction.setId(UUID.randomUUID());
        } else {
            hashSet.remove(transaction);
        }
        hashSet.add(transaction);
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "transactions.json")) {
            str = new ObjectMapper().writeValueAsString(hashSet);
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
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
            logger.info(e.getMessage());
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH_TO_DATA + "transactions.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public Collection<JsonTransaction> getTransactionsUser(User<?> user) {
        List<JsonTransaction> transactionList = new LinkedList<>();
        AccountRepository<Account<?, ?, ?, ?>> repositoryAccount = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        repositoryAccount.findAll().stream().filter(r -> r.getUserId() == user.getId())
                .forEach(r -> transactionList.addAll(
                        findAll().stream().filter(a -> a.getAccountToId() == user.getId()
                                || a.getAccountFromId() == user.getId()).collect(Collectors.toList())));
        return transactionList;
    }
}

