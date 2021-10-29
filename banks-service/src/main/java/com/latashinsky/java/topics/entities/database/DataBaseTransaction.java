package com.latashinsky.java.topics.entities.database;

import com.latashinsky.java.topics.entities.Transaction;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class DataBaseTransaction implements Transaction<DataBaseAccount> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "account_from_id", insertable = false, updatable = false)
    private UUID accountFromId;

    @Column(name = "account_to_id", insertable = false, updatable = false)
    private UUID accountToId;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DataBaseAccount getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(DataBaseAccount accountFrom) {
        this.accountFrom = accountFrom;
    }

    public DataBaseAccount getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(DataBaseAccount accountTo) {
        this.accountTo = accountTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(UUID idAccountFrom) {
        this.accountFromId = idAccountFrom;
    }

    public UUID getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(UUID idAccountTo) {
        this.accountToId = idAccountTo;
    }
}
