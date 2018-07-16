package com.alizada.business_keeper_task.service;

import com.alizada.business_keeper_task.log.Logger;
import com.alizada.business_keeper_task.model.ExchangeRateModel;
import com.alizada.business_keeper_task.repository.ExchangeRateRepository;
import com.alizada.business_keeper_task.util.ExchangeRateApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeService {

    private ExchangeRateApi exchangeRateApi;
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeService(ExchangeRateApi exchangeRateApi, ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateApi = exchangeRateApi;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public String calculateExchangeRateData(String date, String baseCurrency, String targetCurrency) {
        Logger.getLogger().info("Path Variables -> Date:" + date + " Base:" + baseCurrency + " Target:" + targetCurrency);

        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date requestedDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(requestedDate);

            Double sumOfDatesRate = 0.0;
            Double helperPreviousDayExchangeRate = 0.0;
            String rateTrend = "";
            Integer daysIndex = 0;

            while (daysIndex < 5) {
                calendar.add(Calendar.DATE, -1);

                //(excluding Sunday and Saturday
                if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                    continue;
                }

                //get exchange rate by path variables
                Double exchangeRateByDate = exchangeRateByDateAndCurrency(date, baseCurrency, targetCurrency);
                if(exchangeRateByDate == null){
                    Logger.getLogger().error("Incorrect path variables");
                    exchangeRateModel.setMessage("Incorrect path variables");
                    return exchangeRateModel.errorResponseJson();
                }

                sumOfDatesRate+=exchangeRateByDate;

                if(daysIndex == 0){
                    //requested date rate
                    exchangeRateModel.setRate(exchangeRateByDate);
                }else {
                    //cases for find trend of last 5 days
                    if ((rateTrend.equals("") || rateTrend.equals("descending")) && exchangeRateByDate < helperPreviousDayExchangeRate) {
                        rateTrend = "descending";
                    } else if ((rateTrend.equals("") || rateTrend.equals("ascending")) && exchangeRateByDate > helperPreviousDayExchangeRate) {
                        rateTrend = "ascending";
                    } else if ((rateTrend.equals("") || rateTrend.equals("constant")) && exchangeRateByDate.equals(helperPreviousDayExchangeRate)) {
                        rateTrend = "constant";
                    } else {
                        rateTrend = "undefined";
                    }
                }

                helperPreviousDayExchangeRate = exchangeRateByDate;

                daysIndex++;
            }

            //last 5 days average of rates
            Double fivePreviousDaysRateAverage = (Double) sumOfDatesRate/5;
            exchangeRateModel.setRateAverage(fivePreviousDaysRateAverage);
            exchangeRateModel.setTrend(rateTrend);
            exchangeRateModel.setCreatedDate(new Date());

            //insert successful data to H2DB
            addCurrentExchangeDateToDB(exchangeRateModel);

            return exchangeRateModel.successResponseJson();

        }catch (Exception ex){
            Logger.getLogger().error(ex.getMessage(), ex);
            exchangeRateModel.setMessage(ex.getMessage());
            return exchangeRateModel.errorResponseJson();
        }
    }
    
    private Double exchangeRateByDateAndCurrency(String date, String baseCurrency, String targetCurrency){
        String apiResponse = exchangeRateApi.getExchangeRatesJsonByDateAndCurrency(date, baseCurrency, targetCurrency);

        if(apiResponse == null){
            return null;
        }
        try {
            //Map json and get rates
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, HashMap<String, Double>> objArray = mapper.readValue(apiResponse, HashMap.class);

            if(objArray.get("error") != null){
                Logger.getLogger().error(objArray.get("error"));
                return null;
            }

            HashMap<String, Double> currenciesRates = objArray.get("rates");
            return currenciesRates.get(targetCurrency);
        } catch (Exception ex){
            Logger.getLogger().error(ex.getMessage(), ex);
            return null;
        }
    }

    private void addCurrentExchangeDateToDB(ExchangeRateModel exchangeRateModel){
        try {
            exchangeRateRepository.save(exchangeRateModel);
        }catch (Exception ex){
            Logger.getLogger().error(ex.getMessage(), ex);
        }
    }
}
