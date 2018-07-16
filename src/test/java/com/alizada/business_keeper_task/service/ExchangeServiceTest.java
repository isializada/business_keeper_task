package com.alizada.business_keeper_task.service;

import com.alizada.business_keeper_task.repository.ExchangeRateRepository;
import com.alizada.business_keeper_task.util.ExchangeRateApiImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExchangeServiceTest {

    @Mock
    private RestOperations restOperations;
    private ExchangeRateApiImpl exchangeRateApi;
    private ExchangeService exchangeService;

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    private static String EXCHANGE_API_RESPONSE = "{\"base\":\"EUR\",\"date\":\"2018-07-05\",\"rates\":{\"USD\":1.1709,\"RUB\":73.7787}}";
    private static String URL_EXCHANGE_API = "https://exchangeratesapi.io/api/2018-07-05?symbols=USD,RUB";

    @Before
    public void setup() {
        restOperations = Mockito.mock(RestOperations.class);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API),any())).thenReturn(EXCHANGE_API_RESPONSE);
        exchangeRateApi = new ExchangeRateApiImpl(restOperations);
        exchangeService = new ExchangeService(exchangeRateApi, exchangeRateRepository);
    }

    @Test
    public void shouldReturnEstimatedExchangeRate() throws Exception{
        //GIVEN
        String date = "2018-07-05";
        String base = "USD";
        String target = "RUB";
        //WHEN
        String exchangeRateResponse = exchangeService.calculateExchangeRateData(date, base, target);

        ObjectMapper map = new ObjectMapper();
        HashMap<String, Object> rateMap = map.readValue(exchangeRateResponse, HashMap.class);

        //THEN

        assertThat((Double) rateMap.get("rate")).isEqualTo(73.7787);
        assertThat((Double) rateMap.get("rateAverage")).isEqualTo(73.7787);
        assertThat(rateMap.get("trend").toString()).isEqualTo("constant");
    }
}
