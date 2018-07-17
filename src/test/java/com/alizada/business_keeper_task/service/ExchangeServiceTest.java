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

    private static String EXCHANGE_API_RESPONSE_DAY_1 = "{\"base\":\"EUR\",\"date\":\"2018-07-06\",\"rates\":{\"USD\":1.1724,\"RUB\":74.0505}}";
    private static String EXCHANGE_API_RESPONSE_DAY_2 = "{\"base\":\"EUR\",\"date\":\"2018-07-05\",\"rates\":{\"USD\":1.1709,\"RUB\":73.7787}}";
    private static String EXCHANGE_API_RESPONSE_DAY_3 = "{\"base\":\"EUR\",\"date\":\"2018-07-04\",\"rates\":{\"USD\":1.1642,\"RUB\":73.6646}}";
    private static String EXCHANGE_API_RESPONSE_DAY_4 = "{\"base\":\"EUR\",\"date\":\"2018-07-03\",\"rates\":{\"USD\":1.1665,\"RUB\":73.6468}}";
    private static String EXCHANGE_API_RESPONSE_DAY_5 = "{\"base\":\"EUR\",\"date\":\"2018-07-02\",\"rates\":{\"USD\":1.1639,\"RUB\":73.4691}}";
    private static String URL_EXCHANGE_API_DAY_1 = "https://exchangeratesapi.io/api/2018-07-06?symbols=USD,RUB";
    private static String URL_EXCHANGE_API_DAY_2 = "https://exchangeratesapi.io/api/2018-07-05?symbols=USD,RUB";
    private static String URL_EXCHANGE_API_DAY_3 = "https://exchangeratesapi.io/api/2018-07-04?symbols=USD,RUB";
    private static String URL_EXCHANGE_API_DAY_4 = "https://exchangeratesapi.io/api/2018-07-03?symbols=USD,RUB";
    private static String URL_EXCHANGE_API_DAY_5 = "https://exchangeratesapi.io/api/2018-07-02?symbols=USD,RUB";

    @Before
    public void setup() {
        restOperations = Mockito.mock(RestOperations.class);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API_DAY_1),any())).thenReturn(EXCHANGE_API_RESPONSE_DAY_1);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API_DAY_2),any())).thenReturn(EXCHANGE_API_RESPONSE_DAY_2);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API_DAY_3),any())).thenReturn(EXCHANGE_API_RESPONSE_DAY_3);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API_DAY_4),any())).thenReturn(EXCHANGE_API_RESPONSE_DAY_4);
        when(restOperations.getForObject(eq(URL_EXCHANGE_API_DAY_5),any())).thenReturn(EXCHANGE_API_RESPONSE_DAY_5);
        exchangeRateApi = new ExchangeRateApiImpl(restOperations);
        exchangeService = new ExchangeService(exchangeRateApi, exchangeRateRepository);
    }

    @Test
    public void shouldReturnEstimatedExchangeRate() throws Exception{
        //GIVEN
        String date = "2018-07-08";
        String base = "USD";
        String target = "RUB";
        //WHEN
        String exchangeRateResponse = exchangeService.calculateExchangeRateData(date, base, target);

        ObjectMapper map = new ObjectMapper();
        HashMap<String, Object> rateMap = map.readValue(exchangeRateResponse, HashMap.class);

        //THEN

        assertThat((Double) rateMap.get("rate")).isEqualTo(74.0505);
        assertThat((Double) rateMap.get("rateAverage")).isEqualTo(73.72193999999999);
        assertThat(rateMap.get("trend").toString()).isEqualTo("descending");
    }
}
