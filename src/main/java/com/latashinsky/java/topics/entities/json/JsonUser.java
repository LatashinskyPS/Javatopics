package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.UserTypes;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonUser implements User {
    private UUID id = UUID.randomUUID();

    private String name;

    private UserTypes userType;

    @JsonIgnore
    private List<Account> accounts;

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

    public List<? extends Account> getAccounts() {
        return accounts;
    }

    @SuppressWarnings("unchecked")
    public void setAccounts(List<? extends Account> accounts) {
        this.accounts = (List<Account>) accounts;
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
}
