package com.latashinsky.java.topics.entities.database;

import com.latashinsky.java.topics.entities.Currency;
import com.latashinsky.java.topics.entities.CurrencyExchange;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "currencies")
public class DataBaseCurrency implements Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 3)
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private List<DataBaseCurrencyExchange> currencyExchanges;

    public List<DataBaseCurrencyExchange> getCurrencyExchanges() {
        return currencyExchanges;
    }

    @SuppressWarnings("unchecked")
    public void setCurrencyExchanges(List<? extends CurrencyExchange> currencyExchanges) {
        this.currencyExchanges = (List<DataBaseCurrencyExchange>) currencyExchanges;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
