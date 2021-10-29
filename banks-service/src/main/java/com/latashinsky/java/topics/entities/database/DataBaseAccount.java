package com.latashinsky.java.topics.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class DataBaseAccount implements Account<DataBaseTransaction, DataBaseCurrency, DataBaseBank, DataBaseUser> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "bank_id", insertable = false, updatable = false)
    private UUID bankId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private UUID userId;

    @Column(name = "currency_id", insertable = false, updatable = false)
    private UUID currencyId;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private DataBaseCurrency currency;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    protected DataBaseBank bank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DataBaseUser user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_from_id")
    @JsonIgnore
    private List<DataBaseTransaction> transactionsFrom;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_to_id")
    @JsonIgnore
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
        return id.equals(account.id);
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


    @Override
    public DataBaseUser getUser() {
        return user;
    }

    @Override
    public void setUser(DataBaseUser user) {
        this.user = user;
    }

    @Override
    public List<DataBaseTransaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    @Override
    public void setTransactionsFrom(List<DataBaseTransaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    @Override
    public List<DataBaseTransaction> getTransactionsTo() {
        return transactionsTo;
    }

    @Override
    public void setTransactionsTo(List<DataBaseTransaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public void setCurrency(DataBaseCurrency currency) {
        this.currency = currency;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getBankId() {
        return bankId;
    }

    @Override
    public void setBankId(UUID bankId) {
        this.bankId = id;
    }

    @Override
    public UUID getUserId() {
        return userId;
    }

    @Override
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public UUID getCurrencyId() {
        return currencyId;
    }

    @Override
    public void setCurrencyId(UUID currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public DataBaseCurrency getCurrency() {
        return currency;
    }

    @Override
    public DataBaseBank getBank() {
        return bank;
    }

    @Override
    public void setBank(DataBaseBank bank) {
        this.bank = bank;
    }
}
