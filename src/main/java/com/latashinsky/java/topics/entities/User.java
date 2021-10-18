package com.latashinsky.java.topics.entities;

import com.latashinsky.java.topics.helpers.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public interface User {
    Logger logger = LoggerFactory.getLogger(User.class);

    List<? extends Account> getAccounts();

    void setAccounts(List<? extends Account> accounts);

    int getId();

    void setId(int id);

    String getName();

    void setName(String name);

    UserTypes getUserType();

    void setUserType(UserTypes userType);
}
