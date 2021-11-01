package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.repositories.BankRepository;
import com.latashinsky.banksservice.services.BankService;
import com.latashinsky.banksservice.utils.Confirms;
import com.latashinsky.banksservice.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class BankSettingsController extends BaseSettingsController<Bank<Account<?, ?, ?, ?>>> {
    private static final Logger logger = LoggerFactory.getLogger(BankSettingsController.class);
    private static BankSettingsController bankSettingsController;
    protected BankRepository<Bank<?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private BankSettingsController() {
    }

    public static BankSettingsController getInstance() {
        if (bankSettingsController == null) {
            bankSettingsController = new BankSettingsController();
        }
        return bankSettingsController;
    }

    @Override
    public void show(Bank<Account<?, ?, ?, ?>> bank) {
        logger.info(bank + "\n" + "Accounts:");
        AccountRepository<Account<?, ?, ?, ?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        List<Account<?, ?, ?, ?>> list = (List<Account<?, ?, ?, ?>>) myRepository.getAccountsBank(bank);
        bank.setAccounts(list);
        logger.info(list + "\n");
    }

    public void update(Bank<Account<?, ?, ?, ?>> bank) {
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
                BankService.getInstance().editName(bank);
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
                BankService.getInstance().editUsualCommission(bank);
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
                BankService.getInstance().editLegalCommission(bank);
                break;
            }
        }
        myRepository.save(bank);
    }

    @Override
    public boolean delete(Bank<Account<?, ?, ?, ?>> bank) {

        logger.info(bank + "\n");
        if (Confirms.confirm()) {
            myRepository.delete(bank);
            return true;
        }
        return false;
    }
}
