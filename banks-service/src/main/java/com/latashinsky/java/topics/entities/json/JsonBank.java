package com.latashinsky.java.topics.entities.json;

import com.latashinsky.java.topics.entities.Bank;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class JsonBank implements Bank<JsonAccount> {

    private UUID id = UUID.randomUUID();

    private String name;

    private BigDecimal usualCommission;

    private BigDecimal legalCommission;

    @JsonIgnore
    private List<JsonAccount> accounts;

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
        JsonBank bank = (JsonBank) o;
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

    public List<JsonAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<JsonAccount> accounts) {
        this.accounts = accounts;
    }
}
