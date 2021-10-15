package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.helpers.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.UserSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserController extends BaseShowAndCreateController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static UserController userController;
    MyRepository<User> userRepository = Factory.getInstance().getRepository(User.class);

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
        User user = Factory.getInstance().getEntity(User.class);
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
