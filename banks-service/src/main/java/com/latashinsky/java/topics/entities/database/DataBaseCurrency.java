package com.latashinsky.java.topics.entities.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.latashinsky.java.topics.entities.Currency;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "currencies")
public class DataBaseCurrency implements Currency<DataBaseCurrencyExchange> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "name", length = 3)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    @JsonIgnore
    private List<DataBaseCurrencyExchange> currencyExchanges;

    public List<DataBaseCurrencyExchange> getCurrencyExchanges() {
        return currencyExchanges;
    }

    public void setCurrencyExchanges(List<DataBaseCurrencyExchange> currencyExchanges) {
        this.currencyExchanges = currencyExchanges;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
