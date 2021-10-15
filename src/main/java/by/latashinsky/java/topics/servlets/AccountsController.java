package by.latashinsky.java.topics.servlets;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.exceptions.BadRequest;
import by.latashinsky.java.topics.exceptions.ResourceNotFound;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping("/accounts")
public class AccountsController {
    private final MyRepository<Account> accountMyRepository = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);

    @GetMapping("")
    public String getAccounts(@RequestParam(value = "id-bank", required = false) final Integer idBank,
                              @RequestParam(value = "id-user", required = false) final Integer idUser,
                              @RequestParam(value = "currency", required = false) final String str) throws JsonProcessingException {
        Collection<Account> users = accountMyRepository.findAll();
        if (idBank != null) {
            users.removeIf(r -> !idBank.equals(r.getIdBank()));
        }
        if (idUser != null) {
            users.removeIf(r -> !idUser.equals(r.getIdUser()));
        }
        if (str != null) {
            users.removeIf(r -> !str.equalsIgnoreCase(r.getCurrency()));
        }
        return new ObjectMapper().writeValueAsString(users);
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") final int id) throws JsonProcessingException {
        Account account = accountMyRepository.findById(id);
        if (account != null) {
            return new ObjectMapper().writeValueAsString(account);
        }
        throw new ResourceNotFound();
    }

    @PostMapping("")
    public String addNewAccount(@RequestParam(value = "id-bank") final Integer idBank,
                                @RequestParam(value = "id-user") final Integer idUser,
                                @RequestParam(value = "balance") final BigDecimal balance,
                                @RequestParam(value = "currency") final String str) {
        if (idBank == 0 || idUser == 0
                || balance == null || str == null) {
            throw new BadRequest();
        }
        Account account = new Account();
        int currencyLength = 3;
        if (str.length() == currencyLength) {
            account.setCurrency(str);
        } else {
            throw new BadRequest();
        }
        account.setIdBank(idBank);
        account.setIdUser(idUser);
        account.setBalance(balance);
        accountMyRepository.save(account);
        return "success";
    }

    @PutMapping("/{id}")
    public String updateAccount(@RequestParam(value = "id-bank", required = false) final Integer idBank,
                                @RequestParam(value = "id-user", required = false) final Integer idUser,
                                @RequestParam(value = "balance", required = false) final BigDecimal balance,
                                @RequestParam(value = "currency", required = false) final String str,
                                @PathVariable("id") int id) {
        Account account = accountMyRepository.findById(id);
        int currencyLength = 3;
        if (str != null) {
            if (str.length() == currencyLength) {
                account.setCurrency(str);
            } else {
                throw new BadRequest();
            }
        }
        if (account == null) {
            throw new ResourceNotFound();
        }
        if (idBank != null) {
            account.setIdBank(idBank);
        }
        if (idUser != null) {
            account.setIdUser(idUser);
        }
        if (balance != null) {
            account.setBalance(balance);
        }
        accountMyRepository.save(account);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable("id") final int id) {
        Account account = accountMyRepository.findById(id);
        if (account == null) {
            throw new ResourceNotFound();
        }
        accountMyRepository.delete(account);
        return "success";
    }
}
