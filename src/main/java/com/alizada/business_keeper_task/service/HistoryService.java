package com.alizada.business_keeper_task.service;

import com.alizada.business_keeper_task.log.Logger;
import com.alizada.business_keeper_task.model.ExchangeRateModel;
import com.alizada.business_keeper_task.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public HistoryService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public List<ExchangeRateModel> getDailyRateHistory(String year, String month, String day) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            //make a date object by requested data and get history from DB
            Date requestedDate = format.parse(year + "/" + month + "/" + day);
            return exchangeRateRepository.getExchangeRateDataByDate(requestedDate);
        }catch (Exception ex){
            Logger.getLogger().error(ex.getMessage(),ex);
            return null;
        }
    }

    public List<ExchangeRateModel> getMonthlyRateHistory(String year, String month) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Integer yearNum = Integer.valueOf(year);
            Integer monthNum = Integer.valueOf(month);
            //make date object of 1st day of requested month
            Date requestedMonthDate = format.parse(year + "/" + month + "/01");
            Date nextMonthDate;

            //make date object of 1st day of the next month
            if(monthNum == 12){
                Integer nextYear = yearNum + 1;
                nextMonthDate = format.parse(nextYear.toString() + "/01/01");
            }else{
                Integer nextMonth = monthNum + 1;
                nextMonthDate = format.parse(year + "/" + nextMonth.toString() + "/01");
            }

            //get list of data between dates
            return exchangeRateRepository.getExchangeRateDataByMonth(requestedMonthDate, nextMonthDate);
        } catch (Exception ex){
            Logger.getLogger().error(ex.getMessage(),ex);
            return null;
        }

    }
}
