package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.UserTypes;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonUser implements User<JsonAccount> {
    private UUID id = UUID.randomUUID();

    private String name;

    private UserTypes userType;

    @JsonIgnore
    private List<JsonAccount> accounts;

    private String chatId;

    @Email
    private String email;

    private boolean enabledNotifications;

    @Override
    public String toString() {
        return String.format("%s)%s\nUser type:%s\nAccounts:\n%s",
                id, name, userType, MyListConverter.convert(accounts));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonUser user = (JsonUser) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<JsonAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<JsonAccount> accounts) {
        this.accounts = accounts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabledNotifications() {
        return enabledNotifications;
    }

    public void setEnabledNotifications(boolean enabledNotifications) {
        this.enabledNotifications = enabledNotifications;
    }
}
