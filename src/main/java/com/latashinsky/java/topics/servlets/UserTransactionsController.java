package com.latashinsky.java.topics.servlets;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions/{id}")
public class UserTransactionsController {
    private final MyRepository<User> userMyRepository = Factory.getInstance().getRepository(User.class);
    private final MyRepository<Transaction> transactionMyRepository = Factory.getInstance().getRepository(Transaction.class);

    @GetMapping("")
    public String getUserTransactions(@PathVariable("id") int id,
                                      @RequestParam(value = "date-from", required = false) Long dateFrom,
                                      @RequestParam(value = "date-to", required = false) Long dateTo) throws JsonProcessingException {
        User user = userMyRepository.findById(id);
        TransactionRepository<Transaction> transactionRepository = (TransactionRepository<Transaction>) transactionMyRepository;
        Set<Transaction> transactionHashSet = new HashSet<>(transactionRepository.getTransactionsUser(user));
        if (dateFrom != null) {
            transactionHashSet.removeIf(r -> r.getDate().before(new Date(dateFrom)));
        }
        if (dateTo != null) {
            transactionHashSet.removeIf(r -> r.getDate().after(new Date(dateTo)));
        }
        return new ObjectMapper().writeValueAsString(transactionHashSet);
    }
}
