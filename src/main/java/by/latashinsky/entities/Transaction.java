package by.latashinsky.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_account_from")
    @JsonIgnore
    //@todo
    private Account accountFrom;

    @ManyToOne
    @JoinColumn(name = "id_account_to")
    @JsonIgnore
    //@todo
    private Account accountTo;

    @Column(name = "id_account_from", insertable = false, updatable = false)
    //@todo
    private int idAccountFrom;

    @Column(name = "id_account_to", insertable = false, updatable = false)
    //@todo
    private int idAccountTo;

    @Override
    public String toString() {
        return
                "id=" + id +
                        ", accountFrom=" + accountFrom.getId() +
                        ", accountTo=" + accountTo.getId() +
                        ", date=" + date +
                        ", value=" + value;
    }

    @Column(name = "transaction_time")
    @Temporal(TemporalType.DATE)
    private Date date;

    private BigDecimal value;

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
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
