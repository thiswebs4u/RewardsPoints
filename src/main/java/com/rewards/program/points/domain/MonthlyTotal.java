package com.rewards.program.points.domain;

public class MonthlyTotal {
    private String month;
    private int total;

    public MonthlyTotal () {
    }
    public MonthlyTotal(String month, int total) {
        super();
        this.month = month;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
