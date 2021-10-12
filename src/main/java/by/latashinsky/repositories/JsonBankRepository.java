package by.latashinsky.repositories;

import by.latashinsky.entities.Bank;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class JsonBankRepository implements MyRepository<Bank> {
    private static final JsonBankRepository jsonBankRepository = new JsonBankRepository();

    private JsonBankRepository() {
    }

    public static JsonBankRepository getInstance() {
        return jsonBankRepository;
    }
    @Override
    public Bank findById(int id) {
        return findAll().stream().filter(r -> r.getId() == id).findAny().orElse(null);
    }

    @Override
    public HashSet<Bank> findAll() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader("banks.json"))) {
            String str = fileReader.readLine();
            return new ObjectMapper().readValue(str, new TypeReference<HashSet<Bank>>() {
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
    public void save(Bank bank) {
        try (FileWriter fileWriter = new FileWriter("banks.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(bank)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Bank bank) {
        try (FileWriter fileWriter = new FileWriter("banks.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().removeIf(r -> bank.getId() == r.getId())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
