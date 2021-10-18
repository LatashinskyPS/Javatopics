package com.latashinsky.java.topics.servlets;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.exceptions.BadRequest;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.TransactionManager;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final MyRepository<Account> accountMyRepository = Factory.getInstance().getRepository(Account.class);
    private final MyRepository<Transaction> myRepository = Factory.getInstance().getRepository(Transaction.class);

    @GetMapping("")
    public String getTransactions(@RequestParam(value = "id-account-to", required = false) Integer idAccountTo,
                                  @RequestParam(value = "id-account-from", required = false) Integer idAccountFrom) throws JsonProcessingException {
        Collection<Transaction> transactions = myRepository.findAll();
        if (idAccountFrom != null) {
            transactions.removeIf(r -> !idAccountFrom.equals(r.getAccountFromId()));
        }
        if (idAccountTo != null) {
            transactions.removeIf(r -> !idAccountTo.equals(r.getAccountToId()));
        }
        return new ObjectMapper().writeValueAsString(transactions);
    }

    @PostMapping("")
    public String createTransaction(@RequestParam(value = "id-account-to") Integer idAccountTo,
                                    @RequestParam(value = "id-account-from") Integer idAccountFrom,
                                    @RequestParam(value = "value") BigDecimal value) {
        if (idAccountTo == null || idAccountFrom == null
                || value == null) {
            throw new BadRequest();
        }
        Account accountFrom = accountMyRepository.findById(idAccountFrom);
        Account accountTo = accountMyRepository.findById(idAccountTo);
        if (accountFrom.getBalance().compareTo(value) >= 0) {
            if (TransactionManager.doTransaction(accountFrom, accountTo, value)) {
                return "success";
            }
        }
        throw new BadRequest();
    }
}
