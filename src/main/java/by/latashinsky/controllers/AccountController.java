package by.latashinsky.controllers;

import by.latashinsky.entities.Account;
import by.latashinsky.factory.DataBaseRepositoryFactory;
import by.latashinsky.factory.Factory;
import by.latashinsky.models.MyListConverter;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.user.interfaces.AccountSettingsUI;
import by.latashinsky.utils.SelectHelpUtil;

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
