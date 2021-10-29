package com.latashinsky.java.topics.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.latashinsky.java.topics.interfaces.UserSettingsUi;
import com.latashinsky.java.topics.repositories.UserRepository;
import com.latashinsky.java.topics.services.UserService;
import com.latashinsky.java.topics.utils.SelectHelpUtil;
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
        logger.info(MyListConverter.convert(userRepository.findAll()));
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
