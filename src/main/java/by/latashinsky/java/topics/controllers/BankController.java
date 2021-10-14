package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.BankSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankController extends BaseShowAndCreateController<Bank> {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
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
        logger.info(MyListConverter.convert(myRepository.findAll()));
    }
}
