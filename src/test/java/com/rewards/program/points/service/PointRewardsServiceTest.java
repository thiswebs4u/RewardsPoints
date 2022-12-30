package com.rewards.program.points.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.program.points.domain.MonthlyTotal;
import com.rewards.program.points.domain.Result;
import com.rewards.program.points.domain.Transaction;
import com.rewards.program.points.domain.Transactions;
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

@SpringBootTest(classes = {PointsService.class})
class PointRewardsServiceTest {
    @Autowired
    PointsService pointsService;

    ObjectMapper mapper;
    TypeReference<List<Result>> typeRef;

    Transactions allTransactions = null;

    @BeforeEach
    void setUp() throws ParseException {
        allTransactions = new Transactions();
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 90, "10/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 90, "10/05/22"));
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 100, "11/10/22"));
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 100, "11/12/22"));
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 120, "12/10/22"));
        allTransactions.getAllTransactions().add(new Transaction("Joe Jackson", 120, "12/12/22"));

        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 90, "10/10/22"));
        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 90, "10/15/22"));
        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 100, "11/10/22"));
        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 100, "11/25/22"));
        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 120, "12/10/22"));
        allTransactions.getAllTransactions().add(new Transaction("Jill Gentry", 120, "12/25/22"));

        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 90, "10/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 10, "10/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 100, "11/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 120, "11/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 120, "12/01/22"));
        allTransactions.getAllTransactions().add(new Transaction("Bill Hart", 120, "12/25/22"));

        typeRef
                = new TypeReference<List<Result>>() {
        };
        mapper = new ObjectMapper();
    }
    // /calcPoints

    @Test
    void testServiceMethods() throws Exception {

        Map<String, List<Transaction>> customerToTransactionsMap = pointsService.getTransactionsByCustomerName(allTransactions);

        List<Result> result = pointsService.buildResults(customerToTransactionsMap);


        /**
         * Calculate for Joe Jackson
         */
        Map<String, List<Integer>> transactionsByMonth = pointsService.getTransactionsByMonth(customerToTransactionsMap.get("Joe Jackson"));

        int total = pointsService.getTotal(transactionsByMonth);

        Assertions.assertEquals(3, transactionsByMonth.size());

        int totalCompare = transactionsByMonth.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);

        Assertions.assertEquals(total, totalCompare);

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

        Assertions.assertEquals(3, transactionsByMonth.size());

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

        Assertions.assertEquals(3, transactionsByMonth.size());

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


        Assertions.assertEquals(0, pointsService.calcPoints(0));
        Assertions.assertEquals(0, pointsService.calcPoints(-5));
        Assertions.assertEquals(0, pointsService.calcPoints(50));
        Assertions.assertEquals(50, pointsService.calcPoints(100));
        Assertions.assertEquals(52, pointsService.calcPoints(101));
    }
}