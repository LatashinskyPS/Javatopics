package com.latashinsky.banksservice.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public interface CurrencyExchange {
    Date getDate();

    void setDate(Date date);

    UUID getCurrencyId();

    void setCurrencyId(UUID currencyId);

    BigDecimal getRate();

    void setRate(BigDecimal rate);

    UUID getId();

    void setId(UUID id);
}
