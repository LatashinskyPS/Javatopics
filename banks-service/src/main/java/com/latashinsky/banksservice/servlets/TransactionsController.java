package com.latashinsky.banksservice.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Transaction;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.TransactionManager;
import com.latashinsky.banksservice.services.MessageService;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.exceptions.BadRequest;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.TransactionRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;


@Tag(name = "Transactions controller")
@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final MessageService messageService;
    private final AccountRepository<Account<?, ?, ?, ?>> accountMyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final TransactionRepository<Transaction<?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    public TransactionsController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Получение транзакций по фильтрам")
    @GetMapping("")
    public Collection<Transaction<?>> getTransactions(@RequestParam(value = "id-account-to", required = false) UUID idAccountTo,
                                                      @RequestParam(value = "id-account-from", required = false) UUID idAccountFrom) {
        Collection<Transaction<?>> transactions = myRepository.findAll();
        if (idAccountFrom != null) {
            transactions.removeIf(r -> !idAccountFrom.equals(r.getAccountFromId()));
        }
        if (idAccountTo != null) {
            transactions.removeIf(r -> !idAccountTo.equals(r.getAccountToId()));
        }
        return transactions;
    }

    @Operation(summary = "Создание(проведение) транзации")
    @PostMapping("")
    public boolean createTransaction(@RequestParam(value = "id-account-to") UUID idAccountTo,
                                     @RequestParam(value = "id-account-from") UUID idAccountFrom,
                                     @RequestParam(value = "value") BigDecimal value) {
        if (idAccountTo == null || idAccountFrom == null
                || value == null) {
            throw new BadRequest();
        }
        Account<?, ?, ?, ?> accountFrom = accountMyRepository.findById(idAccountFrom);
        Account<?, ?, ?, ?> accountTo = accountMyRepository.findById(idAccountTo);
        if (accountFrom.getBalance().compareTo(value) >= 0) {
            if (TransactionManager.doTransaction(accountFrom, accountTo, value)) {
                User<?> userFrom = userRepository.findById(accountFrom.getUserId());
                User<?> userTo = userRepository.findById(accountTo.getUserId());
                String amount = value + " " + accountFrom.getCurrency().toString();
                if (userFrom != null) {
                    messageService.sendMessage(userFrom, "transaction from:\n" + accountFrom
                            + "\nAmount:" + amount, "transactions.create");
                }
                if (userTo != null) {
                    messageService.sendMessage(userTo, "transaction to:\n" + accountTo
                            + "\nAmount:" + amount, "transactions.create");
                }
                return true;
            }
        }
        throw new BadRequest();
    }
}
