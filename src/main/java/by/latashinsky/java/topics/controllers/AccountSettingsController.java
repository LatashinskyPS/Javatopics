package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.utils.Confirms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class AccountSettingsController extends BaseSettingsController<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsController.class);
    private static AccountSettingsController accountSettingsController;
    protected MyRepository<Account> myRepository = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);

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
                account.editBank();
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
                account.editUser();
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
                account.editBalance();
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
                account.editCurrency();
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
