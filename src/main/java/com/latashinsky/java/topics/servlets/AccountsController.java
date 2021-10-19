package com.latashinsky.java.topics.servlets;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.exceptions.BadRequest;
import com.latashinsky.java.topics.exceptions.ResourceNotFound;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.CurrencyRepository;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final MyRepository<Account> accountMyRepository = Factory.getInstance().getRepository(Account.class);
    private final CurrencyRepository<Currency> currencyRepository =
            (CurrencyRepository<Currency>) Factory.getInstance().getRepository(Currency.class);

    @GetMapping("")
    public String getAccounts(@RequestParam(value = "id-bank", required = false) final UUID idBank,
                              @RequestParam(value = "id-user", required = false) final UUID idUser,
                              @RequestParam(value = "currency", required = false) final String str) throws JsonProcessingException {
        Collection<Account> users = accountMyRepository.findAll();
        if (idBank != null) {
            users.removeIf(r -> !idBank.equals(r.getBankId()));
        }
        if (idUser != null) {
            users.removeIf(r -> !idUser.equals(r.getUserId()));
        }
        if (str != null) {
            users.removeIf(r -> !str.equalsIgnoreCase(r.getCurrency().getName()));
        }
        return new ObjectMapper().writeValueAsString(users);
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") final UUID id) throws JsonProcessingException {
        Account account = accountMyRepository.findById(id);
        if (account != null) {
            return new ObjectMapper().writeValueAsString(account);
        }
        throw new ResourceNotFound();
    }

    @PostMapping("")
    public String addNewAccount(@RequestParam(value = "id-bank") final UUID idBank,
                                @RequestParam(value = "id-user") final UUID idUser,
                                @RequestParam(value = "balance") final BigDecimal balance,
                                @RequestParam(value = "currency") final String str) {
        if (idBank == null || idUser == null
                || balance == null || str == null) {
            throw new BadRequest();
        }
        Account account = Factory.getInstance().getEntity(Account.class);
        Currency currency = currencyRepository.findByName(str);
        if (currency != null) {
            account.setCurrency(currency);
        } else {
            throw new BadRequest();
        }
        account.setBankId(idBank);
        account.setUserId(idUser);
        account.setBalance(balance);
        accountMyRepository.save(account);
        return "success";
    }

    @PutMapping("/{id}")
    public String updateAccount(@RequestParam(value = "id-bank", required = false) final UUID idBank,
                                @RequestParam(value = "id-user", required = false) final UUID idUser,
                                @RequestParam(value = "balance", required = false) final BigDecimal balance,
                                @RequestParam(value = "currency", required = false) final String str,
                                @PathVariable("id") UUID id) {
        Account account = accountMyRepository.findById(id);
        if (str != null) {
            Currency currency = currencyRepository.findByName(str);
            if (currency != null) {
                account.setCurrency(currency);
            } else {
                throw new BadRequest();
            }
        }
        if (account == null) {
            throw new ResourceNotFound();
        }
        if (idBank != null) {
            account.setBankId(idBank);
        }
        if (idUser != null) {
            account.setUserId(idUser);
        }
        if (balance != null) {
            account.setBalance(balance);
        }
        accountMyRepository.save(account);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable("id") final UUID id) {
        Account account = accountMyRepository.findById(id);
        if (account == null) {
            throw new ResourceNotFound();
        }
        accountMyRepository.delete(account);
        return "success";
    }
}
