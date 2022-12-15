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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointRewardsMockitoControllerTest {
    @InjectMocks
    PointController pointController;

    @Mock
    PointsService pointsService;

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
        transactions.getTransactions().add(new Transaction("Jill Gentry", 120, "12/25/22"));

        transactions.getTransactions().add(new Transaction("Bill Hart", 90, "10/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 10, "10/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 100, "11/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120, "11/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120, "12/01/22"));
        transactions.getTransactions().add(new Transaction("Bill Hart", 120, "12/25/22"));
        typeRef
                = new TypeReference<List<Result>>() {
        };
        mapper = new ObjectMapper();
    }

    @Test
    public void postTest() throws Exception {

        List<Result> serviceResult = Arrays.asList(new Result("Joe Jackson",
                Arrays.asList(new MonthlyTotal("OCTOBER", 80),
                        new MonthlyTotal("DECEMBER", 80),
                        new MonthlyTotal("NOVEMBER", 100)),
                260));

        when(pointsService.buildResults(any())).thenReturn(serviceResult);

        ResponseEntity<String> controllerResult = pointController.getPoints(transactions);

        assertEquals(controllerResult.getStatusCode(), HttpStatus.OK);
        List<Result> jsonToJavaObject = mapper.readValue(controllerResult.getBody(), typeRef);
        assertEquals(serviceResult.size(), jsonToJavaObject.size());
    }
}