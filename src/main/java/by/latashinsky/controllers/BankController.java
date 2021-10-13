package by.latashinsky.controllers;

import by.latashinsky.entities.Bank;
import by.latashinsky.factory.DataBaseRepositoryFactory;
import by.latashinsky.factory.Factory;
import by.latashinsky.models.MyListConverter;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.user.interfaces.BankSettingsUI;
import by.latashinsky.utils.SelectHelpUtil;

import java.util.Scanner;

public class BankController extends BaseShowAndCreateController<Bank> {
    private static BankController bankController;
    protected MyRepository<Bank> myRepository = (MyRepository<Bank>) Factory.getInstance().getRepository(Bank.class);

    private BankController() {
    }

    public static BankController getInstance() {
        if (bankController == null) {
            bankController = new BankController();
        }
        return bankController;
    }

    public void create() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        Bank bank = new Bank();
        bank.editName();
        bank.editLegalCommission();
        bank.editUsualCommission();
        myRepository.save(bank);
    }

    public void read() {
        Bank bank = SelectHelpUtil.selectBank();
        if(bank!=null)BankSettingsUI.run(bank);
    }

    public void show() {
        System.out.print(MyListConverter.convert(myRepository.findAll()));
    }
}
