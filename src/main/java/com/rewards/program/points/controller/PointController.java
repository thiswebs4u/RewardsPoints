package com.rewards.program.points.controller;

import com.rewards.program.points.controller.Points.Results;
import com.rewards.program.points.controller.Points.Util;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;


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
            list.add(MonthlyTotal.builder().month(month).total(monthTransactions.get(month).stream().reduce(0, Integer::sum)).build());
        }
        return list;
    }

    public List<Results> buildResults(Map<String, List<Transaction>> map) {
        List<Results> results = map
                .values()
                .stream()
                .flatMap(list -> list.stream())
                .map(t -> {
                    Map<String, List<Integer>> transActionsByMonth = getTransactionsByMonth(map.get(t.getCustomer()));
                    return Results.builder()
                            .customer(t.getCustomer())
                            .monthlyTotals(getTransActionsByMonthTotals(transActionsByMonth))
                            .total(getTotal(transActionsByMonth))
                            .build();
                })
                .collect(Collectors.toList());

        return results;
    }

    public int getTotal(Map<String, List<Integer>> monthlyTransactions) {
        return monthlyTransactions.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);
    }

    @PostMapping(path ="/test", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public /*List<Results>*/ String getPoints(@RequestBody Transactions transactions) throws ParseException {
        int points = 0;
        Map<String, List<Transaction>> map = transactionsByName(transactions);

//        return buildResults(map);
        return "good";
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
