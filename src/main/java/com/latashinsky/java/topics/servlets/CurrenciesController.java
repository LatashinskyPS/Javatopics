package com.latashinsky.java.topics.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.exceptions.BadRequest;
import com.latashinsky.java.topics.exceptions.ResourceNotFound;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/currencies")
public class CurrenciesController {
    CurrencyRepository<Currency> currencyRepository =
            (CurrencyRepository<Currency>) Factory.getInstance().getRepository(Currency.class);

    @GetMapping("")
    public String getCurrencies() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(currencyRepository.findAll());
    }

    @PostMapping("")
    public String createCurrency(
            @RequestParam(name = "currency") String currencyName) throws JsonProcessingException {
        if (currencyName == null) {
            throw new BadRequest();
        }
        Currency currency = Factory.getInstance().getEntity(Currency.class);
        if (currencyName.length() == 3 && currencyRepository.findByName(currencyName.toUpperCase(Locale.ROOT)) == null) {
            currency.setName(currencyName.toUpperCase(Locale.ROOT));
            currencyRepository.save(currency);
            return new ObjectMapper().writeValueAsString(currency);
        } else {
            throw new BadRequest();
        }
    }

    @PutMapping("/{id}")
    public String updateCurrency(
            @RequestParam(name = "currency") String currencyName,
            @PathVariable(name = "id") UUID id) throws JsonProcessingException {
        Currency currency = currencyRepository.findById(id);
        if (currency == null) {
            throw new ResourceNotFound();
        }
        if (currencyName != null && currencyName.length() == 3
                && currencyRepository.findByName(currencyName.toUpperCase(Locale.ROOT)) == null) {
            currency.setName(currencyName);
            currencyRepository.save(currency);
            return new ObjectMapper().writeValueAsString(currency);
        } else {
            throw new BadRequest();
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCurrency(@PathVariable(value = "id") UUID id) {
        Currency currency = currencyRepository.findById(id);
        if (currency != null) {
            currencyRepository.delete(currency);
            return null;
        } else {
            throw new BadRequest();
        }
    }
}
