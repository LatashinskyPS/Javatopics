package by.latashinsky.java.topics.controllers;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import by.latashinsky.java.topics.utils.Confirms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserSettingsController extends BaseSettingsController<User> {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingsController.class);
    private static UserSettingsController userSettingsController;
    protected MyRepository<User> myRepository = Factory.getInstance().getRepository(User.class);
    private final MyRepository<Account> myAccountRepository = Factory.getInstance().getRepository(Account.class);

    private UserSettingsController() {
    }

    public static UserSettingsController getInstance() {
        if (userSettingsController == null) {
            userSettingsController = new UserSettingsController();
        }
        return userSettingsController;
    }

    @Override
    public void show(User user) {
        ArrayList<Account> list = myAccountRepository.findAll()
                .stream().filter(r -> r.getIdUser() == user.getId()).collect(Collectors.toCollection(ArrayList::new));
        user.setAccounts(list);
        logger.info(user + "\n");
    }

    @Override
    public void update(User user) {
        logger.info(user + "\n");
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        logger.info("Are you want to edit user name(y/n)?\n");
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
        logger.info("Are you want to edit user type(y/n)?\n");
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

        logger.info(user + "\n");
        if (Confirms.confirm()) {
            myRepository.delete(user);
            return true;
        }
        return false;
    }
}
