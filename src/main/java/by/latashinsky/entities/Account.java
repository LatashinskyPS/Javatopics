package by.latashinsky.entities;

import by.latashinsky.models.Constants;
import by.latashinsky.utils.SelectHelpUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.jfr.Name;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Account() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "id_bank")
    @JsonIgnore
    //@todo
    private Bank bank;

    @Column(name = "id_bank", insertable = false, updatable = false)
    @JsonIgnore
    //@todo
    private int idBank;

    @Column(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    //@todo
    private int idUser;

    public boolean editBank() {
        Bank bank = SelectHelpUtil.selectBank();
        if (bank == null) {
            return false;
        }
        this.bank = bank;
        return true;
    }

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account_from")
    @JsonIgnore
    private List<Transaction> transactionsFrom;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account_to")
    @JsonIgnore
    private List<Transaction> transactionsTo;

    public List<Transaction> getTransactionsFrom() {
        return transactionsFrom;
    }

    public void setTransactionsFrom(List<Transaction> transactionsFrom) {
        this.transactionsFrom = transactionsFrom;
    }

    public List<Transaction> getTransactionsTo() {
        return transactionsTo;
    }

    public void setTransactionsTo(List<Transaction> transactionsTo) {
        this.transactionsTo = transactionsTo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean editUser() {
        User user = SelectHelpUtil.selectUser();
        if (user == null) {
            return false;
        }
        this.user = user;
        return true;
    }

    private BigDecimal balance;

    public boolean editBalance() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            System.out.print("Enter value of balance(exit to return to menu):");
            str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str)) {
                this.balance = new BigDecimal(str);
                return true;
            }
            if ("exit".equals(str)) {
                return false;
            }
            System.out.println("Invalid input!");
        }
    }

    private String currency;

    public boolean editCurrency() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        String str;
        while (true) {
            System.out.print("Enter value of balance(exit to return to menu):");
            str = in.next();
            if (str.length() < 4) {
                this.currency = str;
                return true;
            }
            if ("exit".equals(str)) {
                return false;
            }
            System.out.println("Invalid input!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
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
        this.bank = bank;
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
