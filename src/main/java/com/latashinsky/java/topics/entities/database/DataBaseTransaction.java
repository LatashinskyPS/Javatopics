package com.latashinsky.java.topics.entities.database;

import com.latashinsky.java.topics.entities.Account;
import com.latashinsky.java.topics.entities.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class DataBaseTransaction implements Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "account_from_id", insertable = false, updatable = false)
    private int accountFromId;

    @Column(name = "account_to_id", insertable = false, updatable = false)
    private int accountToId;

    @Column(name = "transaction_time")
    @Temporal(TemporalType.DATE)
    private Date date;

    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "account_from_id")
    private DataBaseAccount accountFrom;

    @ManyToOne
    @JoinColumn(name = "account_to_id")
    private DataBaseAccount accountTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataBaseTransaction that = (DataBaseTransaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return
                "id=" + id
                        + ", accountFrom=" + accountFrom.getId()
                        + ", accountTo=" + accountTo.getId()
                        + ", date=" + date
                        + ", value=" + value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = (DataBaseAccount) accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = (DataBaseAccount) accountTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(int idAccountFrom) {
        this.accountFromId = idAccountFrom;
    }

    public int getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(int idAccountTo) {
        this.accountToId = idAccountTo;
    }
}
