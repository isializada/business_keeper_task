package com.alizada.business_keeper_task.util;

import com.alizada.business_keeper_task.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class ExchangeRateApiImpl implements ExchangeRateApi {
    private RestOperations restOperations;

    @Autowired
    public ExchangeRateApiImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public String getExchangeRatesJsonByDateAndCurrency(String date, String baseCurrency, String targetCurrency) {
        String exchangeURI = "https://exchangeratesapi.io/api/" + date + "?symbols=" + baseCurrency + "," + targetCurrency;
        try {
            //get request to exchangerateapi.io for the JSON rate data
            return restOperations.getForObject(exchangeURI, String.class);
        }catch (Exception ex) {
            Logger.getLogger().error(ex.getMessage(),ex);
            return null;
        }
    }
}
