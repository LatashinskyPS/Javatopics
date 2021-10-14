package by.latashinsky.java.topics.repositories;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JsonAccountRepository implements MyRepository<Account> {
    private static final JsonAccountRepository jsonAccountRepository = new JsonAccountRepository();

    private JsonAccountRepository() {
    }

    public static JsonAccountRepository getInstance() {
        return jsonAccountRepository;
    }

    private Account update(Account account) {
        if (account == null) return null;
        account.setTransactionsFrom(new ArrayList<>());
        List<Transaction> arrayListFrom = account.getTransactionsFrom();
        account.setTransactionsTo(new ArrayList<>());
        List<Transaction> arrayListTo = account.getTransactionsTo();
        account.setBank(JsonBankRepository.getInstance().findById(account.getIdBank()));
        account.setUser(JsonUserRepository.getInstance().findById(account.getIdUser()));
        return account;
    }

    @Override
    public Account findById(int id) {
        Account account = findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
        return update(account);
    }

    @Override
    public HashSet<Account> findAll() {
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/accounts.json"))) {
            json = fileReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (json == null) return new HashSet<>();
        HashSet<Account> hashSet = null;
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<Account>>() {
            });
            hashSet.forEach(this::update);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (hashSet == null) return new HashSet<>();
        return hashSet;
    }

    @Override
    public void save(Account account) {
        if (account.getBank() != null && account.getUser() != null) {
            account.setIdBank(account.getBank().getId());
            account.setIdUser(account.getUser().getId());
        }
        if (account.getIdBank() == 0) return;
        String str = "[]";
        HashSet<Account> hashSet = findAll();
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
        try (FileWriter fileWriter = new FileWriter("data/accounts.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Account account) {
        if (account == null) {
            return;
        }
        String str = "[]";
        try {
            HashSet<Account> hashSet = findAll();
            hashSet.removeIf(r -> account.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("data/accounts.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
