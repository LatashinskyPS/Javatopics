package com.latashinsky.java.topics.helpers;

import com.latashinsky.java.topics.entities.Currency;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

public class JsonCurrencyExchangeRateHelper implements CurrencyExchangeRateHelper {
    @Override
    public HashMap<String, BigDecimal> getCurrencyExchangeRate() {
        HashMap<String, BigDecimal> hashMap = new HashMap<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader("data/currencies.json"))) {
            String json = fileReader.readLine();
            if (json == null) {
                return new HashMap<>();
            }
            HashSet<Currency> hashSet = new ObjectMapper().readValue(json, new TypeReference<HashSet<Currency>>() {
            });
            hashSet.forEach(r -> hashMap.put(r.getName(), r.getValue()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
        return hashMap;
    }
}
