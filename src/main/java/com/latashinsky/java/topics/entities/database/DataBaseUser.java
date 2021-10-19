package com.latashinsky.java.topics.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.User;
import com.latashinsky.java.topics.entities.UserTypes;
import com.latashinsky.java.topics.factory.Factory;
import com.latashinsky.java.topics.helpers.MyListConverter;
import com.latashinsky.java.topics.repositories.AccountRepository;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class DataBaseUser implements User {
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private List<DataBaseAccount> accounts;

    @Override
    public String toString() {
        return String.format("%s)%s\nUser type:%s\nAccounts:\n%s",
                id, name, userType, MyListConverter.convert(
                        ((AccountRepository<Account>) Factory.getInstance().getRepository(Account.class)).getAccountsUser(this)));
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

    public List<? extends Account> getAccounts() {
        return accounts;
    }

    @SuppressWarnings("unchecked")
    public void setAccounts(List<? extends Account> accounts) {
        this.accounts = (List<DataBaseAccount>) accounts;
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
