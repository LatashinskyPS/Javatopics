package by.latashinsky.repositories;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonBankRepository implements MyRepository<Bank> {
    private static final JsonBankRepository jsonBankRepository = new JsonBankRepository();

    private JsonBankRepository() {
    }

    private Bank update(Bank bank) {
        if (bank == null) return null;
        bank.setAccounts(new ArrayList<>());
        List<Account> accounts = bank.getAccounts();
        JsonAccountRepository.getInstance().findAll().stream().forEach(
                r -> {
                    if (r.getIdBank() == bank.getId()) {
                        accounts.add(r);
                    }
                }
        );
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
        try (BufferedReader fileReader = new BufferedReader(new FileReader("banks.json"))) {
            String str = fileReader.readLine();
            HashSet<Bank> hashSet = new ObjectMapper().readValue(str, new TypeReference<HashSet<Bank>>() {});
            hashSet.forEach(this::update);
            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
                return null;
            }
            return new HashSet<>();
        }

        @Override
        public void save (Bank bank){
            try (FileWriter fileWriter = new FileWriter("banks.json")) {
                fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(bank)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void delete (Bank bank){
            try (FileWriter fileWriter = new FileWriter("banks.json")) {
                fileWriter.write(new ObjectMapper().writeValueAsString(findAll().removeIf(r -> bank.getId() == r.getId())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
