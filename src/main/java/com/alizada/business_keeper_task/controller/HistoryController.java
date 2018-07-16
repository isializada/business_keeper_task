package com.alizada.business_keeper_task.controller;

import com.alizada.business_keeper_task.model.ExchangeRateModel;
import com.alizada.business_keeper_task.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/exchange-rate")
public class HistoryController {

    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @RequestMapping(value = "/history/daily/{year}/{month}/{day}", method = RequestMethod.GET)
    public List<ExchangeRateModel> DailyHistory(@PathVariable("year") String year,
                                                @PathVariable("month") String month,
                                                @PathVariable("day") String day){
        return historyService.getDailyRateHistory(year, month, day);
    }

    @RequestMapping(value = "/history/monthly/{year}/{month}", method = RequestMethod.GET)
    public List<ExchangeRateModel> MonthlyHistory(@PathVariable("year") String year,
                                                  @PathVariable("month") String month){
        return historyService.getMonthlyRateHistory(year, month);
    }

}
