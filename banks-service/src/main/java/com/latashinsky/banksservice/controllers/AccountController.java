package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.helpers.CollectionConverter;
import com.latashinsky.banksservice.interfaces.AccountSettingsUi;
import com.latashinsky.banksservice.services.AccountService;
import com.latashinsky.banksservice.utils.SelectHelpUtil;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
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
        logger.info(CollectionConverter.convert(myRepository.findAll()));
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
