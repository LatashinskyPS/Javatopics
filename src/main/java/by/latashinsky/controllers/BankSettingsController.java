package by.latashinsky.controllers;

import by.latashinsky.entities.Account;
import by.latashinsky.entities.Bank;
import by.latashinsky.factory.DataBaseRepositoryFactory;
import by.latashinsky.factory.Factory;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.utils.Confirms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BankSettingsController extends BaseSettingsController<Bank> {
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
        System.out.println(list);
    }

    public void update(Bank bank) {
        System.out.println(bank);
        System.out.println("Are you want to edit name(y/n)?");
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
            System.out.println("Are you want to edit usual commission(y/n)?");
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
            System.out.println("Are you want to legal commission(y/n)?");
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

        System.out.println(bank);
        if (Confirms.confirm()) {
            myRepository.delete(bank);
            return true;
        }
        return false;
    }
}
