package com.alizada.business_keeper_task.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;
import java.util.regex.Matcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ExchangeControllerIT {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldReturn200Status() throws Exception {
        String date = "2018-07-05";
        String base = "USD";
        String target = "RUB";
        this.mockMvc.perform(get("/api/exchange-rate/" + date + "/" + base + "/" + target))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnExchangeRate() throws Exception {
        String date = "2018-07-05";
        String base = "USD";
        String target = "RUB";
        this.mockMvc.perform(get("/api/exchange-rate/" + date + "/" + base + "/" + target))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rate", Matchers.equalTo(74.0505)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rateAverage", Matchers.equalTo(73.72193999999999)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trend", Matchers.equalTo("descending")))
                .andDo(MockMvcResultHandlers.print());
    }

}
