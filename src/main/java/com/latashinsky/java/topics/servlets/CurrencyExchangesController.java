package com.latashinsky.java.topics.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;
import com.latashinsky.java.topics.exceptions.ResourceNotFound;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.CurrencyExchangeRepository;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("currencies/{id}")
public class CurrencyExchangesController {
    CurrencyExchangeRepository<CurrencyExchange> currencyExchangeRepository =
            (CurrencyExchangeRepository<CurrencyExchange>) Factory.getInstance().getRepository(CurrencyExchange.class);
    CurrencyRepository<Currency> currencyRepository =
            (CurrencyRepository<Currency>) Factory.getInstance().getRepository(Currency.class);

    @GetMapping("")
    public String getCurrencyExchanges(@PathVariable(value = "id") int id) throws JsonProcessingException {
        Currency currency = currencyRepository.findById(id);
        if (currency == null) {
            throw new ResourceNotFound();
        }
        Collection<CurrencyExchange> currencyExchanges = currencyExchangeRepository.findByCurrency(currency);
        if (currencyExchanges == null) {
            return "[]";
        } else {
            return new ObjectMapper().writeValueAsString(currencyExchanges);
        }
    }
}
