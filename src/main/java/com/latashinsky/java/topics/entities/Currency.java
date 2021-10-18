package com.latashinsky.java.topics.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

public interface Currency {
    String getName();

    void setName(String name);

    BigDecimal getValue();

    void setValue(BigDecimal value);

    int getId();

    void setId(int id);
}
