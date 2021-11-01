package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.CollectionConverter;
import com.latashinsky.banksservice.interfaces.BankSettingsUi;
import com.latashinsky.banksservice.repositories.BankRepository;
import com.latashinsky.banksservice.services.BankService;
import com.latashinsky.banksservice.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankController extends BaseShowAndCreateController {
    private static final Logger logger = LoggerFactory.getLogger(BankController.class);
    private static BankController bankController;
    protected BankRepository<Bank<?>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private BankController() {
    }

    public static BankController getInstance() {
        if (bankController == null) {
            bankController = new BankController();
        }
        return bankController;
    }

    public void create() {
        Bank<?> bank = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        BankService bankService = BankService.getInstance();
        bankService.editName(bank);
        bankService.editLegalCommission(bank);
        bankService.editUsualCommission(bank);
        myRepository.save(bank);
    }

    public void read() {
        Bank<Account<?, ?, ?, ?>> bank = SelectHelpUtil.selectBank();
        if (bank != null) {
            BankSettingsUi.run(bank);
        }
    }

    public void show() {
        logger.info(CollectionConverter.convert(myRepository.findAll()));
    }
}
