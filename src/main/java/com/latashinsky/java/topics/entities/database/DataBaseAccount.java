package com.latashinsky.java.topics.entities.database;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Bank;
import com.latashinsky.java.topics.entities.Transaction;
import com.latashinsky.java.topics.entities.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class DataBaseAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    @Column(name = "bank_id", insertable = false, updatable = false)
    private int bankId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    private String currency;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    protected DataBaseBank bank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DataBaseUser user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from_id")
    private List<DataBaseTransaction> transactionsFrom;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to_id")
    private List<DataBaseTransaction> transactionsTo;

    public DataBaseAccount() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBaseAccount account = (DataBaseAccount) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Account id:%s\nBank:%s\nBalance:%s\nCurrency:%s",
                id, bank.getName().toUpperCase(Locale.ROOT), balance, currency);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = (DataBaseUser) user;
    }

    public List<DataBaseTransaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    @SuppressWarnings("unchecked")
    public void setTransactionsFrom(List<? extends Transaction> transactionsFrom) {
        this.transactionsFrom = (List<DataBaseTransaction>) transactionsFrom;
    }

    public List<DataBaseTransaction> getTransactionsTo() {
        return transactionsTo;
    }

    @SuppressWarnings("unchecked")
    public void setTransactionsTo(List<? extends Transaction> transactionsTo) {
        this.transactionsTo = (List<DataBaseTransaction>) transactionsTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = (DataBaseBank) bank;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int idBank) {
        this.bankId = idBank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int idUser) {
        this.userId = idUser;
    }
}
