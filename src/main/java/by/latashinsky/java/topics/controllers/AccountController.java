package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.AccountSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountController extends BaseShowAndCreateController<Account> {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    protected MyRepository<Account> myRepository = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);
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
        Account account = new Account();
        if (account.editBank() && account.editUser() &&
                account.editBalance() && account.editCurrency()) {
            myRepository.save(account);
        }
    }

    @Override
    void read() {
        Account account = SelectHelpUtil.selectAccount();
        if(account!=null) AccountSettingsUI.run(account);
    }
}
