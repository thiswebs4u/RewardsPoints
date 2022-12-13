package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/*

         // 1 point for every dollar spent between $50 and $100 i

        if (dollars<=100) {
            points = 1*(dollars - 50);
        }

         // 2 points for every dollar spent over $100

        if (dollars>100) {
            points += 2*(dollars-100);
        }
*/


@RestController
public class PointController {

    /*
     * Given a record of every transaction during a three month period,
     * calculate the reward points earned for each customer per month and total.
     *
     * month
     * transactions
     *  customer, transaction
     *
     * customer, points per month, total points
     */

    /*
     * Get points for dollars spent
     * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points)
     *
     *
     **/

    //@RequestMapping(value = "/test", method = POST, consumes = "application/json",produces = "application/json")

    public Map<String, List<Transaction>> transactionsByName(Transactions transactions) {
        Map<String, List<Transaction>> map = new HashMap<String, List<Transaction>>();

        transactions.getTransactions().forEach(object ->
                map.computeIfAbsent(object.getCustomer(), k -> new ArrayList<>())
                        .add(object));
        return map;
    }

    public Map<String, List<Integer>> getTransactionsByMonth(List<Transaction> trans) {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

        trans.forEach(object ->
                map.computeIfAbsent(object.getMonth(), k -> new ArrayList<>())
                        .add(Util.calcPoints(object.getDollars())));

        return map;
    }

    public List<MonthlyTotal> getTransActionsByMonthTotals(Map<String, List<Integer>> monthTransactions) {
        List list = new ArrayList();

        for (String month : monthTransactions.keySet()) {
            list.add(new MonthlyTotal(month,monthTransactions.get(month).stream().reduce(0, Integer::sum)));
        }
        return list;
    }

    public List<Result> buildResults(Map<String, List<Transaction>> map) {
        List<Result> results = map
                .entrySet()
                .stream()
                .map((e) -> {
                    Map<String, List<Integer>> transActionsByMonth = getTransactionsByMonth(map.get(e.getKey()));
                    return new Result(e.getKey(),
                            getTransActionsByMonthTotals(transActionsByMonth),
                            getTotal(transActionsByMonth));
                })
                .collect(Collectors.toList());
        return results;
    }
    public int getTotal(Map<String, List<Integer>> monthlyTransactions) {
        return monthlyTransactions.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);
    }
    @PostMapping("/test")
    @ResponseBody
    public ResponseEntity<String> getPoints(@RequestBody Transactions transactions) throws JsonProcessingException {
        Map<String, List<Transaction>> map = transactionsByName(transactions);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(buildResults(map));

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

//        /*
//         * 1 point for every dollar spent between $50 and $100 i
//         **/
//
//        if (dollars<100) {
//            points = 1*(dollars - 50);
//        }
//
//        /*
//         * 2 points for every dollar spent over $100
//         **/
//
//        if (dollars>100) {
//            points = 1*50;
//            points += 2*(dollars-100);
//        }
//
//        return points;
}
