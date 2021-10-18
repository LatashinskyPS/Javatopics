package com.latashinsky.java.topics.controllers;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.repositories.AccountRepository;
import com.latashinsky.java.topics.repositories.MyRepository;
import com.latashinsky.java.topics.services.UserService;
import com.latashinsky.java.topics.utils.Confirms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class UserSettingsController extends BaseSettingsController<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsController.class);
    private static UserSettingsController userSettingsController;
    protected MyRepository<User> myRepository = Factory.getInstance().getRepository(User.class);
    private final MyRepository<Account> myAccountRepository = Factory.getInstance().getRepository(Account.class);

    private UserSettingsController() {
    }

    public static UserSettingsController getInstance() {
        if (userSettingsController == null) {
            userSettingsController = new UserSettingsController();
        }
        return userSettingsController;
    }

    @Override
    public void show(User user) {
        AccountRepository<Account> accountRepository = (AccountRepository<Account>) myAccountRepository;
        List<Account> list = (List<Account>) accountRepository.getAccountsUser(user);
        user.setAccounts(list);
        logger.info(user + "\n");
    }

    @Override
    public void update(User user) {
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
    public boolean delete(User user) {

        logger.info(user + "\n");
        if (Confirms.confirm()) {
            myRepository.delete(user);
            return true;
        }
        return false;
    }
}
