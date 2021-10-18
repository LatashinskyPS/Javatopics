package com.latashinsky.java.topics.entities;

import java.math.BigDecimal;
import java.util.Date;

public interface CurrencyExchange {
    String getName();

    void setName(String name);

    BigDecimal getValueTo();

    void setValueTo(BigDecimal valueTo);

    Date getDate();

    void setDate(Date date);

    int getCurrencyId();

    void setCurrencyId(int currencyId);

    BigDecimal getValueIn();

    void setValueIn(BigDecimal valueIn);

    int getId();

    void setId(int id);
}
