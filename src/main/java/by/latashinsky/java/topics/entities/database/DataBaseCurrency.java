package by.latashinsky.java.topics.entities.database;

import by.latashinsky.java.topics.entities.Currency;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "currencies")
public class DataBaseCurrency implements Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
