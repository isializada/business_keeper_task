package com.alizada.business_keeper_task.util;

public interface ExchangeRateApi {
    String getExchangeRatesJsonByDateAndCurrency(String date, String baseCurrency, String targerCurrency);
}
