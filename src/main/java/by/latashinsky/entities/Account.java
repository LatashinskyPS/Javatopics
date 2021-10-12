package by.latashinsky.entities;

import by.latashinsky.models.Constants;
import by.latashinsky.utils.SelectHelpUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "id_bank")
    private Bank bank;

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
    private List<Transaction> transactionsFrom;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_account_to")
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
}
