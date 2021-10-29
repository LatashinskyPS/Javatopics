package com.latashinsky.java.topics.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.exceptions.BadRequest;
import com.latashinsky.java.topics.exceptions.ResourceNotFound;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Locale;
import java.util.UUID;

@Tag(name = "Currency controller", description = "base crud operation on currencies")
@RestController
@RequestMapping("/currencies")
public class CurrenciesController {
    CurrencyRepository<Currency<?>> currencyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    @Operation(summary = "Получение списка валют")
    @GetMapping("")
    public @ResponseBody
    Collection<Currency<?>> getCurrencies() {
        return currencyRepository.findAll();
    }

    @Operation(summary = "Получение валюты по id")
    @GetMapping("/{id}")
    public Currency<?> getCurrency(@PathVariable("id")
                                   @Parameter(name = "id", description = "id валюты") UUID id) {
        Currency<?> currency = currencyRepository.findById(id);
        if (currency != null) {
            return currency;
        }
        throw new ResourceNotFound();
    }

    @Operation(summary = "Создание валюты")
    @PostMapping("")
    public Currency<?> createCurrency(
            @RequestParam(name = "currency-name") String currencyName) {
        if (currencyName == null) {
            throw new BadRequest();
        }
        Currency<?> currency = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        if (currencyName.length() == 3 && currencyRepository.findByName(currencyName.toUpperCase(Locale.ROOT)) == null) {
            currency.setName(currencyName.toUpperCase(Locale.ROOT));
            currencyRepository.save(currency);
            return currency;
        } else {
            throw new BadRequest();
        }
    }

    @Operation(summary = "обновление информации о валюте по id")
    @PutMapping("/{id}")
    public Currency<?> updateCurrency(
            @RequestParam(name = "currency") String currencyName,
            @PathVariable(name = "id") UUID id) {
        Currency<?> currency = currencyRepository.findById(id);
        if (currency == null) {
            throw new ResourceNotFound();
        }
        if (currencyName != null && currencyName.length() == 3
                && currencyRepository.findByName(currencyName.toUpperCase(Locale.ROOT)) == null) {
            currency.setName(currencyName);
            currencyRepository.save(currency);
            return currency;
        } else {
            throw new BadRequest();
        }
    }

    @Operation(summary = "Удаление информации о валюте по id")
    @DeleteMapping("/{id}")
    public boolean deleteCurrency(@PathVariable(value = "id") UUID id) {
        Currency<?> currency = currencyRepository.findById(id);
        if (currency != null) {
            currencyRepository.delete(currency);
            return true;
        } else {
            throw new BadRequest();
        }
    }
}
