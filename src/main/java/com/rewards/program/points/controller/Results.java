package com.rewards.program.points.controller;

import com.rewards.program.points.controller.MonthlyTotal;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
public class Results {
    String customer;
    List<MonthlyTotal> monthlyTotals;
    int total;
}
