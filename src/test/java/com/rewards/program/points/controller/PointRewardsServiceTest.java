package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//public class PointRewardsControllerTest {
//    @WebMvcTest(controllers = PointController.class)
//    @ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PointRewardsServiceTest {
    @Autowired
    PointsService pointsService;

    Transactions transactions = null;
    ObjectMapper mapper;
    TypeReference<List<Result>> typeRef;

    @BeforeEach
    void setUp() throws ParseException {
        transactions = new Transactions();

        transactions.getTransactions().add(new Transaction("joe", 90, "10/01/22"));
        transactions.getTransactions().add(new Transaction("joe", 90, "10/05/22"));
        transactions.getTransactions().add(new Transaction("joe", 100, "11/10/22"));
        transactions.getTransactions().add(new Transaction("joe", 100, "11/12/22"));
        transactions.getTransactions().add(new Transaction("joe", 120, "12/10/22"));
        transactions.getTransactions().add(new Transaction("joe", 120, "12/12/22"));

        transactions.getTransactions().add(new Transaction("jill", 90, "10/10/22"));
        transactions.getTransactions().add(new Transaction("jill", 90, "10/15/22"));
        transactions.getTransactions().add(new Transaction("jill", 100, "11/10/22"));
        transactions.getTransactions().add(new Transaction("jill", 100, "11/25/22"));
        transactions.getTransactions().add(new Transaction("jill", 120, "12/10/22"));
        transactions.getTransactions().add(new Transaction("jill", 120,  "12/25/22"));

        transactions.getTransactions().add(new Transaction("bill", 90,  "10/01/22"));
        transactions.getTransactions().add(new Transaction("bill", 10,  "10/01/22"));
        transactions.getTransactions().add(new Transaction("bill", 100,  "11/01/22"));
        transactions.getTransactions().add(new Transaction("bill", 120,   "11/01/22"));
        transactions.getTransactions().add(new Transaction("bill", 120,  "12/01/22"));
        transactions.getTransactions().add(new Transaction("bill", 120,  "12/25/22"));

        typeRef
                = new TypeReference<List<Result>>() {};
        mapper = new ObjectMapper();
    }
    // /calcPoints

    @Test
    public void postTest() throws Exception {

        List<Result> serviceResult = Arrays.asList(new Result("joe",
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
//            "customer":"joe",
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
            "customer": "joe",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "joe",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "joe",
            "dollars": 100,
            "date": "11/10/22"
        },
        {
            "customer": "joe",
            "dollars": 100,
            "date": "11/12/22"
        },
        {
            "customer": "joe",
            "dollars": 120,
            "date": "12/10/22"
        },
        {
            "customer": "joe",
            "dollars": 120,
            "date": "12/12/22"
        },

    */

