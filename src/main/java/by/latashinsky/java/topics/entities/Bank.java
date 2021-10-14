package by.latashinsky.java.topics.entities;

import by.latashinsky.java.topics.models.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);

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
                logger.info("Invalid input.\n");
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
            logger.info("Enter usual commission:");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                logger.info(str + "\n");
                this.setUsualCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
            }
        }
    }

    public void editLegalCommission() {
        Scanner in = new Scanner(System.in).useDelimiter("\n");
        while (true) {
            logger.info("Enter legal commission:\n");
            String str = in.next();
            if (Pattern.matches(Constants.PATTERN_DOUBLE, str) && Double.parseDouble(str) < 100) {
                this.setLegalCommission(new BigDecimal(str));
                break;
            } else {
                logger.info("Invalid input.\n");
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
