package com.latashinsky.banksservice.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.services.MessageService;
import com.latashinsky.banksservice.exceptions.BadRequest;
import com.latashinsky.banksservice.exceptions.ResourceNotFound;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.BankRepository;
import com.latashinsky.banksservice.repositories.CurrencyRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;

@Tag(name = "Accounts controller")
@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final MessageService messageService;
    private final AccountRepository<Account<?, Currency<?>, ?, ?>> accountMyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final CurrencyRepository<Currency<?>> currencyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final BankRepository<Bank<?>> bankBankRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    public AccountsController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Получение списка аккаунтов с заданными фильтрами")
    @GetMapping("")
    public Collection<Account<?, Currency<?>, ?, ?>> getAccounts(@RequestParam(value = "id-bank", required = false) final UUID idBank,
                                                                 @RequestParam(value = "id-user", required = false) final UUID idUser,
                                                                 @RequestParam(value = "currency", required = false) final String str) {
        Collection<Account<?, Currency<?>, ?, ?>> users = accountMyRepository.findAll();
        if (idBank != null) {
            users.removeIf(r -> !idBank.equals(r.getBankId()));
        }
        if (idUser != null) {
            users.removeIf(r -> !idUser.equals(r.getUserId()));
        }
        if (str != null) {
            users.removeIf(r -> !str.equalsIgnoreCase(r.getCurrency().getName()));
        }
        return users;
    }

    @Operation(summary = "Получение аккаунта по его id")
    @GetMapping("/{id}")
    public Account<?, ?, ?, ?> getAccount(@PathVariable("id") final UUID id) {
        Account<?, ?, ?, ?> account = accountMyRepository.findById(id);
        if (account != null) {
            return account;
        }
        throw new ResourceNotFound();
    }

    @Operation(summary = "Создание нового аккаунта")
    @PostMapping("")
    public boolean addNewAccount(@RequestParam(value = "id-bank") final UUID idBank,
                                 @RequestParam(value = "id-user") final UUID idUser,
                                 @RequestParam(value = "balance") final BigDecimal balance,
                                 @RequestParam(value = "currency") final String str) {
        if (idBank == null || idUser == null
                || balance == null || str == null) {
            throw new BadRequest();
        }
        Account<?, Currency<?>, Bank<?>, User<?>> account = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        Currency<?> currency = currencyRepository.findByName(str);
        if (currency != null) {
            account.setCurrency(currency);
        } else {
            throw new BadRequest();
        }

        User<?> user = userRepository.findById(idUser);
        Bank<?> bank = bankBankRepository.findById(idBank);
        if (user == null || bank == null) {
            throw new BadRequest();
        }
        account.setUser(user);
        account.setBank(bank);
        account.setBankId(idBank);
        account.setUserId(idUser);
        account.setBalance(balance);
        accountMyRepository.save(account);
        messageService.sendMessage(user, account.toString(), "accounts.create");
        return true;
    }

    @Operation(summary = "Обновление информации о аккаунте по id")
    @PutMapping("/{id}")
    public Account<?, Currency<?>, ?, ?> updateAccount(@RequestParam(value = "id-bank", required = false) final UUID idBank,
                                                       @RequestParam(value = "id-user", required = false) final UUID idUser,
                                                       @RequestParam(value = "balance", required = false) final BigDecimal balance,
                                                       @RequestParam(value = "currency", required = false) final String str,
                                                       @PathVariable("id") UUID id) {
        Account<?, Currency<?>, ?, ?> account = accountMyRepository.findById(id);
        if (account == null) {
            throw new ResourceNotFound();
        }
        String message = account.toString();
        if (str != null) {
            Currency<?> currency = currencyRepository.findByName(str);
            if (currency != null) {
                account.setCurrency(currency);
            } else {
                throw new BadRequest();
            }
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

        User<?> user = userRepository.findById(account.getUserId());
        if (user != null) {
            accountMyRepository.save(account);
            message += "\nto\n" + account;
            messageService.sendMessage(user, message, "accounts.put");
        }
        return account;
    }

    @Operation(summary = "Удаление аккаунта по id")
    @DeleteMapping("/{id}")
    public boolean deleteAccount(@PathVariable("id") final UUID id) {
        Account<?, Currency<?>, ?, ?> account = accountMyRepository.findById(id);
        if (account == null) {
            throw new ResourceNotFound();
        }
        User<?> user = userRepository.findById(account.getUserId());
        accountMyRepository.delete(account);
        if (user != null) {
            messageService.sendMessage(user, account.toString(), "accounts.delete");
        }
        return true;
    }
}
