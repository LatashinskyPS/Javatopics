package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.helpers.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.BankSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class BankController extends BaseShowAndCreateController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private static BankController bankController;
    protected MyRepository<Bank> myRepository = Factory.getInstance().getRepository(Bank.class);

    private BankController() {
    }

    public static BankController getInstance() {
        if (bankController == null) {
            bankController = new BankController();
        }
        return bankController;
    }

    public void create() {
        Bank bank = Factory.getInstance().getEntity(Bank.class);
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
