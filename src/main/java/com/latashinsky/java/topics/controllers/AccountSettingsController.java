package com.latashinsky.java.topics.controllers;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.services.AccountService;
import com.latashinsky.java.topics.utils.Confirms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsController extends BaseSettingsController<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsController.class);
    private static AccountSettingsController accountSettingsController;
    protected MyRepository<Account> myRepository = Factory.getInstance().getRepository(Account.class);

    private AccountSettingsController() {
    }

    public static AccountSettingsController getInstance() {
        if (accountSettingsController == null) {
            accountSettingsController = new AccountSettingsController();
        }
        return accountSettingsController;
    }

    @Override
    public void show(Account account) {
        logger.info(account + "\n");
    }

    @SuppressWarnings("checkstyle:Indentation")
    @Override
    public void update(Account account) {
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
    }

    @Override
    public boolean delete(Account account) {

        logger.info(account + "\n");
        if (Confirms.confirm()) {
            myRepository.delete(account);
            return true;
        }
        return false;
    }
}
