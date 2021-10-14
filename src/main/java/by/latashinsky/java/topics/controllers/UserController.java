package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.models.MyListConverter;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.interfaces.UserSettingsUI;
import by.latashinsky.java.topics.utils.SelectHelpUtil;

import java.util.Scanner;

public class UserController extends BaseShowAndCreateController<User> {
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
            System.out.print(MyListConverter.convert(userRepository.findAll()));
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
