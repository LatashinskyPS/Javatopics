package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.utils.Confirms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankSettingsController extends BaseSettingsController<Bank> {
    private static final Logger logger = LoggerFactory.getLogger(BankSettingsController.class);
    private static BankSettingsController bankSettingsController;
    protected MyRepository<Bank> myRepository = (MyRepository<Bank>) Factory.getInstance().getRepository(Bank.class);

    private BankSettingsController() {
    }

    public static BankSettingsController getInstance() {
        if (bankSettingsController == null) {
            bankSettingsController = new BankSettingsController();
        }
        return bankSettingsController;
    }

    @Override
    public void show(Bank bank) {
        MyRepository<Account> myRepository = (MyRepository<Account>) Factory.getInstance().getRepository(Account.class);
        ArrayList<Account> list = myRepository.findAll()
                .stream().filter(r -> r.getIdBank() == bank.getId()).collect(Collectors.toCollection(ArrayList::new));
        bank.setAccounts(list);
        logger.info(list + "\n");
    }

    public void update(Bank bank) {
        logger.info(bank + "\n");
        logger.info("Are you want to edit name(y/n)?\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                bank.editName();
                break;
            }
        }
        while (true) {
            logger.info("Are you want to edit usual commission(y/n)?\n");
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                bank.editUsualCommission();
                break;
            }
        }
        while (true) {
            logger.info("Are you want to legal commission(y/n)?\n");
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                bank.editLegalCommission();
                break;
            }
        }
        myRepository.save(bank);
    }

    @Override
    public boolean delete(Bank bank) {

        logger.info(bank+"\n");
        if (Confirms.confirm()) {
            myRepository.delete(bank);
            return true;
        }
        return false;
    }
}
