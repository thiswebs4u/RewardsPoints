package com.rewards.program.points.controller;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MonthlyTotal {
    String month;
    int total;
}
