package by.latashinsky.controllers;

import by.latashinsky.entities.User;
import by.latashinsky.factory.RepositoryFactory;
import by.latashinsky.repositories.MyRepository;
import by.latashinsky.utils.Confirms;

import java.util.Scanner;

public class UserSettingsController extends BaseSettingsController<User> {
    private static UserSettingsController userSettingsController;
    protected MyRepository<User> myRepository = (MyRepository<User>) new RepositoryFactory().getRepository(User.class);

    private UserSettingsController() {
    }

    public static UserSettingsController getInstance() {
        if (userSettingsController == null) {
            userSettingsController = new UserSettingsController();
        }
        return userSettingsController;
    }

    @Override
    public void update(User user) {
        System.out.println(user);
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Are you want to edit user name(y/n)?");
        String str;
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                user.editName();
                break;
            }
        }
        System.out.println("Are you want to edit user type(y/n)?");
        while (true) {
            str = in.next();
            if ("n".equals(str)) {
                break;
            }
            if ("y".equals(str)) {
                user.editUserType();
                break;
            }
        }
        myRepository.save(user);
    }

    @Override
    public boolean delete(User user) {

        System.out.println(user);
        if (Confirms.confirm()) {
            myRepository.delete(user);
            return true;
        }
        return false;
    }
}
