package com.latashinsky.java.topics.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.TransactionRepository;
import com.latashinsky.java.topics.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/transactions/{id}")
public class UserTransactionsController {
    private final UserRepository<User<?>> userMyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final TransactionRepository<Transaction<?>> transactionMyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    @Operation(summary = "Получение списка транзакции с заданными фильтрами")
    @GetMapping("")
    public Collection<Transaction<?>> getUserTransactions(@PathVariable("id") UUID userId,
                                                          @RequestParam(value = "date-from", required = false) Long dateFrom,
                                                          @RequestParam(value = "date-to", required = false) Long dateTo) {
        User<?> user = userMyRepository.findById(userId);
        Set<Transaction<?>> transactionHashSet = new HashSet<>(transactionMyRepository.getTransactionsUser(user));
        if (dateFrom != null) {
            transactionHashSet.removeIf(r -> r.getDate().before(new Date(dateFrom)));
        }
        if (dateTo != null) {
            transactionHashSet.removeIf(r -> r.getDate().after(new Date(dateTo)));
        }
        return transactionHashSet;
    }
}
