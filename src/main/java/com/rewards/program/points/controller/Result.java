package com.rewards.program.points.controller;

import java.util.List;

public class Result {
    String customer;
    List<MonthlyTotal> monthlyTotals;
    int total;

    public Result(String customer, List<MonthlyTotal> monthlyTotals, int total) {
        this.customer = customer;
        this.monthlyTotals = monthlyTotals;
        this.total = total;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<MonthlyTotal> getMonthlyTotals() {
        return monthlyTotals;
    }

    public void setMonthlyTotals(List<MonthlyTotal> monthlyTotals) {
        this.monthlyTotals = monthlyTotals;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
