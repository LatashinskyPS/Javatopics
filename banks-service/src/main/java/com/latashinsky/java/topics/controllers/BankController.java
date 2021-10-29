package com.latashinsky.java.topics.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.latashinsky.java.topics.repositories.BankRepository;
import com.latashinsky.java.topics.interfaces.BankSettingsUi;
import com.latashinsky.java.topics.services.BankService;
import com.latashinsky.java.topics.utils.SelectHelpUtil;
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
        logger.info(MyListConverter.convert(myRepository.findAll()));
    }
}
