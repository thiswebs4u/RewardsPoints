package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//public class PointRewardsControllerTest {
//    @WebMvcTest(controllers = PointController.class)
//    @ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PointRewardsControllerTest {
    @InjectMocks
    PointController pointController;

    @Mock
    PointsService pointsService;

    @Autowired
    private MockMvc mockMvc;

    Transactions transactions = null;
    ObjectMapper mapper;
    TypeReference<List<Result>> typeRef;

    @BeforeEach
    void setUp() throws ParseException {
        transactions = new Transactions();

        //transactions.getTransactions().add(new Transaction("Joe Jackson", 40, "10/06/22"));
        //transactions.getTransactions().add(new Transaction("Joe Jackson", 40, "10/07/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 90, "10/01/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 90, "10/05/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 100, "11/10/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 100, "11/12/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 120, "12/10/22"));
        transactions.getTransactions().add(new Transaction("Joe Jackson", 120, "12/12/22"));
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

        when(pointsService.buildResults(any())).thenReturn(serviceResult);

        ResponseEntity<String> controllerResult = pointController.getPoints(transactions);

        assertEquals(controllerResult.getStatusCode(), HttpStatus.OK);
        List<Result>  jsonToJavaObject = mapper.readValue(controllerResult.getBody(), typeRef);
        assertEquals(serviceResult.size(), jsonToJavaObject.size());


        System.out.println();
//        mockMvc.perform(post("/calcPoints")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
//        // String json = mapper.writeValueAsString(pointsService.buildResults(map));

    }
}

//    [
//            {
//            "customer":"Joe Jackson",
//            "monthlyTotals":[
//            {
//            "month":"OCTOBER",
//            "total":80
//            },
//            {
//            "month":"DECEMBER",
//            "total":80
//            },
//            {
//            "month":"NOVEMBER",
//            "total":100
//            }
//            ],
//            "total":260
//            }
//            ]


    /*
        {
            "customer": "Joe Jackson",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "Joe Jackson",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "Joe Jackson",
            "dollars": 100,
            "date": "11/10/22"
        },
        {
            "customer": "Joe Jackson",
            "dollars": 100,
            "date": "11/12/22"
        },
        {
            "customer": "Joe Jackson",
            "dollars": 120,
            "date": "12/10/22"
        },
        {
            "customer": "Joe Jackson",
            "dollars": 120,
            "date": "12/12/22"
        },

    */

