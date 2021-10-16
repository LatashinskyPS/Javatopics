package by.latashinsky.java.topics.servlets;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions/{id}")
public class UserTransactionsController {
    private final MyRepository<User> userMyRepository = Factory.getInstance().getRepository(User.class);
    private final MyRepository<Transaction> transactionMyRepository = Factory.getInstance().getRepository(Transaction.class);
    private final MyRepository<Account> accountMyRepository = Factory.getInstance().getRepository(Account.class);

    @GetMapping("")
    public String getUserTransactions(@PathVariable("id") int id,
                                      @RequestParam(value = "date-from", required = false) Long dateFrom,
                                      @RequestParam(value = "date-to", required = false) Long dateTo) throws JsonProcessingException {
        User user = userMyRepository.findById(id);
        Set<Transaction> transactionHashSet = new HashSet<>();
        accountMyRepository.findAll().stream().filter(r -> r.getUserId() == user.getId()).
                forEach(r -> transactionHashSet.addAll(
                        transactionMyRepository.findAll().stream().filter(a -> a.getAccountToId() == user.getId()
                                || a.getAccountFromId() == user.getId()).collect(Collectors.toSet())));
        if (dateFrom != null) {
            transactionHashSet.removeIf(r -> r.getDate().before(new Date(dateFrom)));
        }
        if (dateTo != null) {
            transactionHashSet.removeIf(r -> r.getDate().after(new Date(dateTo)));
        }
        return new ObjectMapper().writeValueAsString(transactionHashSet);
    }
}
