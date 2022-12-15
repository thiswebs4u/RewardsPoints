package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.program.points.domain.MonthlyTotal;
import com.rewards.program.points.domain.Result;
import com.rewards.program.points.domain.Transaction;
import com.rewards.program.points.domain.Transactions;
import com.rewards.program.points.service.PointsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PointController.class)
public class PointRewardsControllerSpringBootTest {

    @MockBean
    PointsService pointsService;

    @Autowired
    private MockMvc mockMvc;

    Transactions transactions = null;
    ObjectMapper mapper;
    TypeReference<List<Result>> typeRef;

    @BeforeEach
    void setUp() throws ParseException {
        transactions = new Transactions();

        transactions.getTransactions().add(new Transaction("Joe Jackson", 90, "10/01/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 90, "10/05/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 100, "11/10/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 100, "11/12/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 120, "12/10/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 120, "12/12/22"));

        transactions.getTransactions().add(new Transaction("Jill Gentry", 90, "10/10/22"));
        transactions.getTransactions().add(new Transaction("Jill Gentry", 90, "10/15/22"));
        transactions.getTransactions().add(new Transaction("Jill Gentry", 100, "11/10/22"));
        transactions.getTransactions().add(new Transaction("Jill Gentry", 100, "11/25/22"));
        transactions.getTransactions().add(new Transaction("Jill Gentry", 120, "12/10/22"));
        transactions.getTransactions().add(new Transaction("Jill Gentry", 120,  "12/25/22"));

        transactions.getTransactions().add(new Transaction("Bill Hart", 90,  "10/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 10,  "10/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 100,  "11/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120,   "11/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120,  "12/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120,  "12/25/22"));
        typeRef
                = new TypeReference<List<Result>>() {};
        mapper = new ObjectMapper();
    }
    // /calcPoints

    @Test
    public void postTest() throws Exception {

        List<Result> serviceResult = Arrays.asList(new Result("Joe Jackson",
                Arrays.asList(new MonthlyTotal("OCTOBER",80),
                        new MonthlyTotal("DECEMBER",80),
                        new MonthlyTotal("NOVEMBER",100)),
                    260));

        Result result = serviceResult.get(0);

        when(pointsService.buildResults(any())).thenReturn(serviceResult);


        String json = mapper.writeValueAsString(transactions);

        mockMvc.perform(post("/calcPoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].customer", is(result.getCustomer())))
                .andExpect(jsonPath("$.[0].monthlyTotals[0].month", is(result.getMonthlyTotals().get(0).getMonth())))
                .andExpect(jsonPath("$.[0].monthlyTotals[1].month", is(result.getMonthlyTotals().get(1).getMonth())))
                .andExpect(jsonPath("$.[0].monthlyTotals[2].month", is(result.getMonthlyTotals().get(2).getMonth())))
                .andExpect(jsonPath("$.[0].monthlyTotals[0].total", is(result.getMonthlyTotals().get(0).getTotal())))
                .andExpect(jsonPath("$.[0].monthlyTotals[1].total", is(result.getMonthlyTotals().get(1).getTotal())))
                .andExpect(jsonPath("$.[0].monthlyTotals[2].total", is(result.getMonthlyTotals().get(2).getTotal())))
                .andExpect(jsonPath("$.[0].total", is(result.getTotal())));
    }
}