package by.latashinsky.java.topics.entities.database;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;
import by.latashinsky.java.topics.entities.Transaction;
import by.latashinsky.java.topics.entities.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class DataBaseAccount implements Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "id_bank", insertable = false, updatable = false)
    private int idBank;

    @Column(name = "id_user", insertable = false, updatable = false)
    private int idUser;

    private String currency;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "id_bank")
    protected DataBaseBank bank;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private DataBaseUser user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account_from")
    private List<DataBaseTransaction> transactionsFrom;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account_to")
    private List<DataBaseTransaction> transactionsTo;

    public DataBaseAccount() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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

    public int getIdBank() {
        return idBank;
    }

    public void setIdBank(int idBank) {
        this.idBank = idBank;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
