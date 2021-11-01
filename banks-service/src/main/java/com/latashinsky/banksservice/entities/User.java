package com.latashinsky.banksservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public interface User<T extends Account<?, ?, ?, ?>> {
    Logger logger = LoggerFactory.getLogger(User.class);

    @JsonIgnore
    List<T> getAccounts();

    @JsonIgnore
    void setAccounts(List<T> accounts);

    UUID getId();

    void setId(UUID id);

    String getName();

    void setName(String name);

    UserTypes getUserType();

    void setUserType(UserTypes userType);

    String getChatId();

    void setChatId(String chatId);

    String getEmail();

    void setEmail(String email);

    boolean isEnabledNotifications();

    void setEnabledNotifications(boolean enabledNotifications);
}
