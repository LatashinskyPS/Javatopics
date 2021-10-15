package by.latashinsky.java.topics.entities.json;

import by.latashinsky.java.topics.entities.Currency;

import java.math.BigDecimal;

public class JsonCurrency implements Currency {
    private int id;
    private String name;
    private BigDecimal value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
