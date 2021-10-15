package by.latashinsky.java.topics.entities.database;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Bank;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "banks")
public class DataBaseBank implements Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "usual_commission")
    private BigDecimal usualCommission;

    @Column(name = "legal_commission")
    private BigDecimal legalCommission;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bank")
    private List<DataBaseAccount> accounts;

    @Override
    public String toString() {
        return String.format("%s)Bank name:%s\nUsual commission:%s%%\nLegal Commission:%s%%\n",
                id, name.toUpperCase(Locale.ROOT), usualCommission, legalCommission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBaseBank bank = (DataBaseBank) o;
        return id == bank.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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

    public List<DataBaseAccount> getAccounts() {
        return accounts;
    }

    @SuppressWarnings("unchecked")
    public void setAccounts(List<? extends Account> accounts) {
        this.accounts = (List<DataBaseAccount>) accounts;
    }
}
