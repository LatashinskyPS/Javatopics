package com.latashinsky.java.topics.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.Constants;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.latashinsky.java.topics.repositories.AccountRepository;
import com.latashinsky.java.topics.interfaces.AccountSettingsUi;
import com.latashinsky.java.topics.repositories.UserRepository;
import com.latashinsky.java.topics.services.AccountService;
import com.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountController extends BaseShowAndCreateController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final AccountRepository<Account<?, ?, ?, ?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private static AccountController accountController;

    private AccountController() {
    }

    public static AccountController getInstance() {
        if (accountController == null) {
            accountController = new AccountController();
        }
        return accountController;
    }

    @Override
    void show() {
        logger.info(MyListConverter.convert(myRepository.findAll()));
    }

    @Override
    void create() {
        Account<?, Currency<?>, Bank<?>, User<?>> account = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        AccountService accountService = AccountService.getInstance();
        if (accountService.editBank(account) && accountService.editUser(account)
                && accountService.editBalance(account) && accountService.editCurrency(account)) {
            myRepository.save(account);
            Constants.messageService.sendMessage(account.getUser(), "create:\n" + account, "accounts.create");
        }
    }

    @Override
    void read() {
        Account<?, Currency<?>, Bank<?>, User<?>> account = SelectHelpUtil.selectAccount();
        if (account != null) {
            AccountSettingsUi.run(account);
        }
    }
}
