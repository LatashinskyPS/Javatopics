package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.services.UserService;
import com.latashinsky.banksservice.utils.Confirms;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class UserSettingsController extends BaseSettingsController<User<Account<?, ?, ?, ?>>> {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsController.class);
    private static UserSettingsController userSettingsController;
    protected UserRepository<User<?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });
    private final AccountRepository<Account<?, ?, ?, ?>> myAccountRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private UserSettingsController() {
    }

    public static UserSettingsController getInstance() {
        if (userSettingsController == null) {
            userSettingsController = new UserSettingsController();
        }
        return userSettingsController;
    }

    @Override
    public void show(User<Account<?, ?, ?, ?>> user) {
        List<Account<?, ?, ?, ?>> list = (List<Account<?, ?, ?, ?>>) myAccountRepository.getAccountsUser(user);
        user.setAccounts(list);
        logger.info(user + "\n");
    }

    @Override
    public void update(User<Account<?, ?, ?, ?>> user) {
        logger.info(user + "\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        logger.info("Are you want to edit user name(y/n)?\n");
        String str;
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                UserService.getInstance().editName(user);
                break;
            }
        }
        logger.info("Are you want to edit user type(y/n)?\n");
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                UserService.getInstance().editUserType(user);
                break;
            }
        }
        myRepository.save(user);
    }

    @Override
    public boolean delete(User<Account<?, ?, ?, ?>> user) {

        logger.info(user + "\n");
        if (Confirms.confirm()) {
            myRepository.delete(user);
            return true;
        }
        return false;
    }
}
