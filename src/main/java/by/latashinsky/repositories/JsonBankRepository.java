package by.latashinsky.repositories;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.models.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JsonBankRepository implements MyRepository<Bank> {
    private static final JsonBankRepository jsonBankRepository = new JsonBankRepository();

    private JsonBankRepository() {
    }

    private Bank update(Bank bank) {
        if (bank == null) return null;
        bank.setAccounts(new ArrayList<>());
        return bank;
    }

    public static JsonBankRepository getInstance() {
        return jsonBankRepository;
    }

    @Override
    public Bank findById(int id) {
        Bank bank = findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
        return update(bank);
    }

    @Override
    public HashSet<Bank> findAll() {
        HashSet<Bank> hashSet = null;
        String json = null;
        try (BufferedReader fileReader = new BufferedReader(new FileReader(Constants.PATH + "banks.json"))) {
            json = fileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (json == null) return new HashSet<>();
        try {
            hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<Bank>>() {
            });
            hashSet.forEach(this::update);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (hashSet == null) return new HashSet<>();
        return hashSet;
    }

    @Override
    public void save(Bank bank) {
        int idMax = findAll().stream().map(Bank::getId).max(Integer::compare).orElse(0);
        HashSet<Bank> hashSet = findAll();
        if (bank.getId() == 0) {
            bank.setId(idMax + 1);
        } else {
            hashSet.remove(bank);
        }
        hashSet.add(bank);
        String str = "empty" ;
        try {
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH + "banks.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Bank bank) {
        if (bank == null) {
            return;
        }
        String str = "[]" ;
        try {
            HashSet<Bank> hashSet = findAll();
            hashSet.removeIf(r -> bank.getId() == r.getId());
            str = new ObjectMapper().writeValueAsString(hashSet);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter(Constants.PATH + "banks.json")) {
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
