package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.MainClass;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.UserSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class UserController extends BaseShowAndCreateController<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static UserController userController;
    MyRepository<User> userRepository = (MyRepository<User>) Factory.getInstance().getRepository(User.class);

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
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        User user = new User();
        user.editName();
        user.editUserType();
        userRepository.save(user);
    }

    @Override
    void read() {
        User user = SelectHelpUtil.selectUser();
        if (user != null) UserSettingsUI.run(user);
    }
}
