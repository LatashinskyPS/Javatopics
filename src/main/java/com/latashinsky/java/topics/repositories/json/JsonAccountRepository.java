package com.latashinsky.java.topics.repositories.json;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.json.JsonAccount;
import com.latashinsky.java.topics.repositories.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class JsonAccountRepository implements AccountRepository<JsonAccount> {
    private static final JsonAccountRepository jsonAccountRepository = new JsonAccountRepository();

    private JsonAccountRepository() {
    }

    public static JsonAccountRepository getInstance() {
        return jsonAccountRepository;
    }

    private JsonAccount update(JsonAccount account) {
        if (account == null) {
            return null;
        }
        account.setTransactionsFrom(new ArrayList<>());
        account.setTransactionsTo(new ArrayList<>());
        account.setBank(JsonBankRepository.getInstance().findById(account.getBankId()));
        account.setUser(JsonUserRepository.getInstance().findById(account.getUserId()));
        return account;
    }

    @Override
    public JsonAccount findById(int id) {
        JsonAccount account = findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
        return update(account);
    }

    @Override
    public HashSet<JsonAccount> findAll() {
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("accounts.json"))) {
            json = fileReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (json == null) {
            return new HashSet<>();
        }

        HashSet<JsonAccount> hashSet = null;
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<JsonAccount>>() {
            });
            hashSet.forEach(this::update);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (hashSet == null) {
            return new HashSet<>();
        }
        return hashSet;
    }

    @Override
    public void save(JsonAccount account) {
        if (account.getBank() != null && account.getUser() != null) {
            account.setBankId(account.getBank().getId());
            account.setUserId(account.getUser().getId());
        }
        if (account.getBankId() == 0) {
            return;
        }
        String str = "[]";
        HashSet<JsonAccount> hashSet = findAll();
        int idMax = findAll().stream().map(Account::getId).max(Integer::compare).orElse(0);
        if (account.getId() == 0) {
            account.setId(idMax + 1);
        } else {
            hashSet.remove(account);
        }
        hashSet.add(account);
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("accounts.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(JsonAccount account) {
        if (account == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<JsonAccount> hashSet = findAll();
            hashSet.removeIf(r -> account.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("accounts.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<JsonAccount> getAccountsBank(Bank bank) {
        return findAll()
                .stream().filter(r -> r.getBankId() == bank.getId())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Collection<JsonAccount> getAccountsUser(User user) {
        return findAll()
                .stream().filter(r -> r.getBankId() == user.getId())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
