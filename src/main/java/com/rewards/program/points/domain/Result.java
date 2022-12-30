package com.rewards.program.points.domain;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String customer;
    private List<MonthlyTotal> monthlyTotals;
    private int total;

    public Result() {
        super();
        monthlyTotals = new ArrayList<>();
    }

    public Result(String customer, List<MonthlyTotal> monthlyTotals, int total) {
        super();
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
