package com.alizada.business_keeper_task.controller;

import com.alizada.business_keeper_task.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ExchangeController {
    private ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @RequestMapping(value = "/exchange-rate/{date}/{baseCurrency}/{targetCurrency}", method = RequestMethod.GET)
    public String ExchangeRate(@PathVariable("date") String date,
                                                  @PathVariable("baseCurrency") String baseCurrency,
                                                  @PathVariable("targetCurrency") String targetCurrency){


        return exchangeService.calculateExchangeRateData(date, baseCurrency, targetCurrency);
    }
}
