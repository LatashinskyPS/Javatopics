package by.latashinsky.repositories;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.entities.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
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
        JsonTransactionRepository.getInstance().findAll().forEach(
                r -> {
                    if (r.getIdAccountFrom() == account.getId()) {
                        arrayListFrom.add(r);
                    }
                }
        );
        account.setTransactionsTo(new ArrayList<>());
        List<Transaction> arrayListTo = account.getTransactionsTo();
        JsonTransactionRepository.getInstance().findAll().forEach(
                r -> {
                    if (r.getIdAccountTo() == account.getId()) {
                        arrayListTo.add(r);
                    }
                }
        );
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
        try (BufferedReader fileReader = new BufferedReader(new FileReader("accounts.json"))) {
            String str = fileReader.readLine();
            HashSet<Account> hashSet = new ObjectMapper().readValue(str, new TypeReference<HashSet<Account>>() {
            });
            hashSet.forEach(this::update);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return new HashSet<>();
    }

    @Override
    public void save(Account account) {
        try (FileWriter fileWriter = new FileWriter("banks.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().add(account)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Account account) {
        try (FileWriter fileWriter = new FileWriter("banks.json")) {
            fileWriter.write(new ObjectMapper().writeValueAsString(findAll().removeIf(r -> account.getId() == r.getId())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
