package com.alizada.business_keeper_task.service;

import com.alizada.business_keeper_task.model.ExchangeRateModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryServiceTest {
    @Autowired
    private HistoryService historyService;

    @Test
    public void shouldReturnNotNullValueForDailyHistory(){
        //GIVEN
        String day = "16";
        String month = "07";
        String year = "2018";
        //WHEN
        List<ExchangeRateModel> dailyRateHistory = historyService.getDailyRateHistory(year,month,day);
        //THEN
        assertThat(dailyRateHistory).isNotNull();
    }

    @Test
    public void shouldReturnNotNullValueForMonthlyHistory(){
        //GIVEN
        String month = "07";
        String year = "2018";
        //WHEN
        List<ExchangeRateModel> monthlyRateHistory = historyService.getMonthlyRateHistory(year,month);
        //THEN
        assertThat(monthlyRateHistory).isNotNull();
    }
}
