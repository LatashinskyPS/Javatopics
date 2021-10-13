package by.latashinsky.controllers;

import by.latashinsky.entities.User;
import by.latashinsky.factory.DataBaseRepositoryFactory;
import by.latashinsky.factory.Factory;
import by.latashinsky.models.MyListConverter;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.user.interfaces.UserSettingsUI;
import by.latashinsky.utils.SelectHelpUtil;

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
