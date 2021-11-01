package com.latashinsky.banksservice.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.CollectionConverter;
import com.latashinsky.banksservice.interfaces.UserSettingsUi;
import com.latashinsky.banksservice.services.UserService;
import com.latashinsky.banksservice.utils.SelectHelpUtil;
import com.latashinsky.banksservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserController extends BaseShowAndCreateController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static UserController userController;
    UserRepository<User<?>> userRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    private UserController() {
    }

    public static UserController getInstance() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    @Override
    void show() {
        logger.info(CollectionConverter.convert(userRepository.findAll()));
    }

    @Override
    public void create() {
        User<?> user = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        UserService.getInstance().editName(user);
        UserService.getInstance().editUserType(user);
        UserService.getInstance().editEmail(user);
        userRepository.save(user);
    }

    @Override
    void read() {
        User<Account<?, ?, ?, ?>> user = SelectHelpUtil.selectUser();
        if (user != null) {
            UserSettingsUi.run(user);
        }
    }
}
