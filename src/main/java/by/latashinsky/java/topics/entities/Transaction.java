package by.latashinsky.java.topics.entities;

import java.math.BigDecimal;
import java.util.Date;

public interface Transaction {
    BigDecimal getValue();

    void setValue(BigDecimal value);

    int getId();

    void setId(int id);

    Account getAccountFrom();

    <T extends Account> void setAccountFrom(T accountFrom);

    Account getAccountTo();

    <T extends Account> void setAccountTo(T accountTo);

    Date getDate();

    void setDate(Date date);

    int getIdAccountFrom();

    void setIdAccountFrom(int idAccountFrom);

    int getIdAccountTo();

    void setIdAccountTo(int idAccountTo);
}
