package by.latashinsky.java.topics.servlets;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.exceptions.BadRequest;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.helpers.TransactionManager;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final MyRepository<Account> accountMyRepository = Factory.getInstance().getRepository(Account.class);
    private final MyRepository<Transaction> myRepository =  Factory.getInstance().getRepository(Transaction.class);

    @GetMapping("")
    public String getTransactions(@RequestParam(value = "id-account-to", required = false) Integer idAccountTo,
                                  @RequestParam(value = "id-account-from", required = false) Integer idAccountFrom) throws JsonProcessingException {
        Collection<Transaction> transactions = myRepository.findAll();
        if (idAccountFrom != null) {
            transactions.removeIf(r -> !idAccountFrom.equals(r.getIdAccountFrom()));
        }
        if (idAccountTo != null) {
            transactions.removeIf(r -> !idAccountTo.equals(r.getIdAccountTo()));
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
            HashMap<String, BigDecimal> currencyExchangeRate
                    = Factory.getInstance().getCurrencyExchangeRateHelper().getCurrencyExchangeRate();
            if (!currencyExchangeRate.containsKey(accountFrom.getCurrency())
                    || !currencyExchangeRate.containsKey(accountTo.getCurrency())) {
                throw new BadRequest();
            }
            TransactionManager.doTransactionWithoutCheck(accountFrom, accountTo, value, currencyExchangeRate);
            return "success";
        } else {
            throw new BadRequest();
        }
    }
}
