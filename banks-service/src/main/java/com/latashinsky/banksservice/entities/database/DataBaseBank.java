package com.latashinsky.banksservice.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.banksservice.entities.Bank;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "banks")
public class DataBaseBank implements Bank<DataBaseAccount> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    private String name;

    @Column(name = "usual_commission")
    private BigDecimal usualCommission;

    @Column(name = "legal_commission")
    private BigDecimal legalCommission;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    @JsonIgnore
    private List<DataBaseAccount> accounts;

    @Override
    public String toString() {
        return String.format("%s)Bank name:%s\nUsual commission:%s%%\nLegal Commission:%s%%\n",
                id, name.toUpperCase(Locale.ROOT), usualCommission, legalCommission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<DataBaseAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<DataBaseAccount> accounts) {
        this.accounts = accounts;
    }
}
