package by.latashinsky.entities;

import by.latashinsky.models.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(name = "usual_commission")
    private BigDecimal usualCommission;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bank")
    @JsonIgnore
    //@todo
    private List<Account> accounts;

    @Override
    public String toString() {
        return String.format("%s)Bank name:%s\nUsual commission:%s%%\nLegal Commission:%s%%\n",
                id, name.toUpperCase(Locale.ROOT), usualCommission, legalCommission);
    }

    @Column(name = "legal_commission")
    private BigDecimal legalCommission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUsualCommission() {
        return usualCommission;
    }

    public void setUsualCommission(BigDecimal usualCommission) {
        this.usualCommission = usualCommission;
    }

    public BigDecimal getLegalCommission() {
        return legalCommission;
    }

    public void setLegalCommission(BigDecimal legalCommission) {
        this.legalCommission = legalCommission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void editName() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.print("Enter name:");
            String str = in.next();
            if (str.length() <= 45 && str.length() >= 3) {
                this.setName(str);
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id == bank.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void editUsualCommission() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.print("Enter usual commission:");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                System.out.println(str);
                this.setUsualCommission(new BigDecimal(str));
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }
    }

    public void editLegalCommission() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            System.out.print("Enter legal commission:");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                this.setLegalCommission(new BigDecimal(str));
                break;
            } else {
                System.out.println("Invalid input.");
            }
        }

    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
