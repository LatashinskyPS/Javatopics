package by.latashinsky.java.topics.entities.database;

import by.latashinsky.java.topics.entities.Account;
import by.latashinsky.java.topics.entities.Transaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class DataBaseTransaction implements Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_account_from", insertable = false, updatable = false)
    private int idAccountFrom;

    @Column(name = "id_account_to", insertable = false, updatable = false)
    private int idAccountTo;

    @Column(name = "transaction_time")
    @Temporal(TemporalType.DATE)
    private Date date;

    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "id_account_from")
    private DataBaseAccount accountFrom;

    @ManyToOne
    @JoinColumn(name = "id_account_to")
    private DataBaseAccount accountTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
                "id=" + id +
                        ", accountFrom=" + accountFrom.getId() +
                        ", accountTo=" + accountTo.getId() +
                        ", date=" + date +
                        ", value=" + value;
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

    public int getIdAccountFrom() {
        return idAccountFrom;
    }

    public void setIdAccountFrom(int idAccountFrom) {
        this.idAccountFrom = idAccountFrom;
    }

    public int getIdAccountTo() {
        return idAccountTo;
    }

    public void setIdAccountTo(int idAccountTo) {
        this.idAccountTo = idAccountTo;
    }
}
