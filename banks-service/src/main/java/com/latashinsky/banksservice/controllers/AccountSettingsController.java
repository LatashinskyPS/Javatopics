package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.Constants;
import com.latashinsky.banksservice.services.AccountService;
import com.latashinsky.banksservice.utils.Confirms;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsController extends BaseSettingsController<Account<?, Currency<?>, Bank<?>, User<?>>> {
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsController.class);
    private static AccountSettingsController accountSettingsController;
    protected AccountRepository<Account<?, ?, ?, ?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private AccountSettingsController() {
    }

    public static AccountSettingsController getInstance() {
        if (accountSettingsController == null) {
            accountSettingsController = new AccountSettingsController();
        }
        return accountSettingsController;
    }

    @Override
    public void show(Account<?, Currency<?>, Bank<?>, User<?>> account) {
        logger.info(account + "\n");
    }

    @Override
    public void update(Account<?, Currency<?>, Bank<?>, User<?>> account) {
        String message = account.toString();
        logger.info(account + "\n");
        logger.info("Are you want to edit bank(y/n)?\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                AccountService.getInstance().editBank(account);
                break;
            }
        }
        while (true) {
            logger.info("Are you want to user(y/n)?\n");
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                AccountService.getInstance().editUser(account);
                break;
            }
        }
        while (true) {
            logger.info("Are you want to balance(y/n)?\n");
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                AccountService.getInstance().editBalance(account);
                break;
            }
        }
        while (true) {
            logger.info("Are you want to currency(y/n)?\n");
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                AccountService.getInstance().editCurrency(account);
                break;
            }
        }
        myRepository.save(account);
        User<?> user = userRepository.findById(account.getUserId());
        if (user != null) {
            message += "\nto\n" + account;
            Constants.messageService.sendMessage(user, "put:\n" + message, "accounts.put");
        }
    }

    @Override
    public boolean delete(Account<?, Currency<?>, Bank<?>, User<?>> account) {

        logger.info(account + "\n");
        if (Confirms.confirm()) {
            User<?> user = userRepository.findById(account.getUserId());
            myRepository.delete(account);
            if (user != null) {
                Constants.messageService.sendMessage(user, "delete:\n" + account, "accounts.delete");
            }
            return true;
        }
        return false;
    }
}
