package com.latashinsky.banksservice.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.entities.Account;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.entities.UserTypes;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.helpers.CollectionConverter;
import com.latashinsky.banksservice.repositories.AccountRepository;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class DataBaseUser implements User<DataBaseAccount> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserTypes userType;

    @Column(name = "chat_id")
    private String chatId;

    @Email
    private String email;

    @Column(name = "enabled_notifications")
    private boolean enabledNotifications;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private List<DataBaseAccount> accounts;

    @Override
    public String toString() {
        AccountRepository<Account<?, ?, ?, ?>> accountRepository = Factory.getInstance().getRepository(new TypeReference<>() {
        });
        return String.format("%s)%s\nUser type:%s\nAccounts:\n%s",
                id, name, userType, CollectionConverter.convert(
                        accountRepository.getAccountsUser(this)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBaseUser user = (DataBaseUser) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<DataBaseAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DataBaseAccount> accounts) {
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

    public void setEnabledNotifications(boolean enabledNotification) {
        this.enabledNotifications = enabledNotification;
    }
}
