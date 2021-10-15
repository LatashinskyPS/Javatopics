package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.helpers.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.AccountSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountController extends BaseShowAndCreateController{
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private final MyRepository<Account> myRepository = Factory.getInstance().getRepository(Account.class);
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
        Account account = Factory.getInstance().getEntity(Account.class);
        if (account.editBank() && account.editUser()
                && account.editBalance() && account.editCurrency()) {
            myRepository.save(account);
        }
    }

    @Override
    void read() {
        Account account = SelectHelpUtil.selectAccount();
        if (account != null) {
            AccountSettingsUI.run(account);
        }
    }
}
