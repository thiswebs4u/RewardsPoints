package com.rewards.program.points.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.program.points.domain.MonthlyTotal;
import com.rewards.program.points.domain.Result;
import com.rewards.program.points.domain.Transaction;
import com.rewards.program.points.domain.Transactions;
import com.rewards.program.points.service.PointsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {PointsService.class})
public class PointRewardsServiceTest {
    @Autowired
    PointsService pointsService;

    ObjectMapper mapper;
    TypeReference<List<Result>> typeRef;

    Transactions transactions = null;

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
    // /calcPoints

    @Test
    public void testServiceMethods() throws Exception {

        Map<String, List<Transaction>> customerToTransactionsMap = pointsService.getTransactionsByCustomerName(transactions);

        List<Result> result = pointsService.buildResults(customerToTransactionsMap);


        /**
         * Calculate for Joe Jackson
         */
        Map<String, List<Integer>> transactionsByMonth = pointsService.getTransactionsByMonth(customerToTransactionsMap.get("Joe Jackson"));

        int total = pointsService.getTotal(transactionsByMonth);

        Assertions.assertEquals(transactionsByMonth.size(), 3);

        int totalCompare = transactionsByMonth.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);

        Assertions.assertEquals(totalCompare, total);

        List<String> months = transactionsByMonth.keySet().stream().collect(Collectors.toList());
        Assertions.assertTrue(months.contains("OCTOBER"));
        Assertions.assertTrue(months.contains("NOVEMBER"));
        Assertions.assertTrue(months.contains("DECEMBER"));

        System.out.println();

        /**
         * Calculate for Jill Gentry
         */
        transactionsByMonth = pointsService.getTransactionsByMonth(customerToTransactionsMap.get("Jill Gentry"));

        total = pointsService.getTotal(transactionsByMonth);

        Assertions.assertEquals(transactionsByMonth.size(), 3);

        totalCompare = transactionsByMonth.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);

        Assertions.assertEquals(totalCompare, total);

        months = transactionsByMonth.keySet().stream().collect(Collectors.toList());
        Assertions.assertTrue(months.contains("OCTOBER"));
        Assertions.assertTrue(months.contains("NOVEMBER"));
        Assertions.assertTrue(months.contains("DECEMBER"));

        /**
         * Calculate for "Bill Hart"
         */
        transactionsByMonth = pointsService.getTransactionsByMonth(customerToTransactionsMap.get("Bill Hart"));

        total = pointsService.getTotal(transactionsByMonth);

        Assertions.assertEquals(transactionsByMonth.size(), 3);

        totalCompare = transactionsByMonth.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);

        Assertions.assertEquals(totalCompare, total);

        months = transactionsByMonth.keySet().stream().collect(Collectors.toList());
        Assertions.assertTrue(months.contains("OCTOBER"));
        Assertions.assertTrue(months.contains("NOVEMBER"));
        Assertions.assertTrue(months.contains("DECEMBER"));

        List<Result> compareResult = Arrays.asList(new Result("Joe Jackson",
                Arrays.asList(new MonthlyTotal("OCTOBER", 80),
                        new MonthlyTotal("DECEMBER", 80),
                        new MonthlyTotal("NOVEMBER", 100)),
                260));

        List<Result> serviceResult =
                pointsService.buildResults(customerToTransactionsMap);


        Assertions.assertEquals(pointsService.calcPoints(0), 0);
        Assertions.assertEquals(pointsService.calcPoints(-5), 0);
        Assertions.assertEquals(pointsService.calcPoints(50), 0);
        Assertions.assertEquals(pointsService.calcPoints(100), 50);
        Assertions.assertEquals(pointsService.calcPoints(101), 52);
    }
}