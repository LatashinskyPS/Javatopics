package com.latashinsky.banksservice.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.CurrencyExchange;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.exceptions.ResourceNotFound;
import com.latashinsky.banksservice.repositories.CurrencyExchangeRepository;
import com.latashinsky.banksservice.repositories.CurrencyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;


@Tag(name = " Currency exchanges controller")
@RestController
@RequestMapping("currencies-exchanges/{id}")
public class CurrencyExchangesController {
    CurrencyExchangeRepository<CurrencyExchange> currencyExchangeRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    CurrencyRepository<Currency<?>> currencyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    @Operation(
            summary = "Получение курса валют",
            description = "Получение курса за всё время по id валюты"
    )
    @GetMapping("")
    public Collection<CurrencyExchange> getCurrencyExchanges(@PathVariable(value = "id") UUID id) {
        Currency<?> currency = currencyRepository.findById(id);
        if (currency == null) {
            throw new ResourceNotFound();
        }
        return currencyExchangeRepository.findByCurrency(currency);
    }
}
