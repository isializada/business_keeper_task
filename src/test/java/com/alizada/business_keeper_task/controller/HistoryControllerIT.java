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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HistoryControllerIT {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldReturn200StatusForDailyHistory() throws Exception {
        String day = "16";
        String month = "07";
        String year = "2018";
        this.mockMvc.perform(get("/api/exchange-rate/history/daily/" + year + "/" + month + "/" + day))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void shouldReturn200StatusForMonthlyHistory() throws Exception {
        String month = "07";
        String year = "2018";
        this.mockMvc.perform(get("/api/exchange-rate/history/monthly/" + year + "/" + month))
                .andDo(print()).andExpect(status().isOk());
    }
}
