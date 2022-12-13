package com.rewards.program.points.controller;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PointsService {

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

    public List<MonthlyTotal> getTransActionsByMonthTotals(Map<String, List<Integer>> monthTransactions) {
        List list = new ArrayList();

        for (String month : monthTransactions.keySet()) {
            list.add(new MonthlyTotal(month,monthTransactions.get(month).stream().reduce(0, Integer::sum)));
        }
        return list;
    }

    public int getTotal(Map<String, List<Integer>> monthlyTransactions) {
        return monthlyTransactions.values().stream()
                .flatMap(list -> list.stream())
                .reduce(0, Integer::sum);
    }
    public Map<String, List<Integer>> getTransactionsByMonth(List<Transaction> trans) {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();

        trans.forEach(object ->
                map.computeIfAbsent(object.getMonth(), k -> new ArrayList<>())
                        .add(Util.calcPoints(object.getDollars())));

        return map;
    }
    public Map<String, List<Transaction>> transactionsByName(Transactions transactions) {
        Map<String, List<Transaction>> map = new HashMap<String, List<Transaction>>();

        transactions.getTransactions().forEach(object ->
                map.computeIfAbsent(object.getCustomer(), k -> new ArrayList<>())
                        .add(object));
        return map;
    }
}
