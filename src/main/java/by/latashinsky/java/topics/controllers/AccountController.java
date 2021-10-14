package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.AccountSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;

public class AccountController extends BaseShowAndCreateController<Account> {
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
        System.out.print(MyListConverter.convert(myRepository.findAll()));
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
