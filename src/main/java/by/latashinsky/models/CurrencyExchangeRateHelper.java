package by.latashinsky.models;

import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrencyExchangeRateHelper {
    HashMap<String, BigDecimal> getCurrencyExchangeRate();
}
