package com.latashinsky.banksservice.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Bank;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.Currency;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.repositories.AccountRepository;
import com.latashinsky.banksservice.repositories.BankRepository;
import com.latashinsky.banksservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class SelectHelpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SelectHelpUtil.class);

    public static Bank<Account<?, ?, ?, ?>> selectBank() {
        BankRepository<Bank<Account<?, ?, ?, ?>>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        myRepository.findAll().forEach(r -> logger.info(r.getId() + ") " + r.getName().toUpperCase(Locale.ROOT) + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of bank(0 to cancel):\n");
            String str = in.next();
            Bank<Account<?, ?, ?, ?>> bank = myRepository.findById(UUID.fromString(str));
            if (bank != null) {
                return bank;
            }
            if ("0".equals(str)) {
                break;
            }
            logger.info("Invalid input!\n");
        }
        return null;
    }

    public static User<Account<?, ?, ?, ?>> selectUser() {
        UserRepository<User<Account<?, ?, ?, ?>>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        myRepository.findAll().forEach(r -> logger.info(r.getId() + ") " + r.getName().toUpperCase(Locale.ROOT) + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of user(0 to cancel):\n");
            String str = in.next();
            User<Account<?, ?, ?, ?>> user = myRepository.findById(UUID.fromString(str));
            if (user != null) {
                return user;
            }
            if ("0".equals(str)) {
                break;
            }
            logger.info("Invalid input!\n");
        }
        return null;
    }

    public static Account<?, Currency<?>, Bank<?>, User<?>> selectAccount() {
        AccountRepository<Account<?, Currency<?>, Bank<?>, User<?>>> myRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        myRepository.findAll().forEach(r -> logger.info(
                r.getId() + ")"
                        + r.getBank().getName() + "->"
                        + r.getUser().getName() + "\n"));
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter id of account(0 to cancel):\n");
            String str = in.next();
            Account<?, Currency<?>, Bank<?>, User<?>> account = myRepository.findById(UUID.fromString(str));
            if (account != null) {
                return account;
            }
            if ("0".equals(str)) {
                break;
            }
            logger.info("Invalid input!\n");
        }
        return null;
    }
}
