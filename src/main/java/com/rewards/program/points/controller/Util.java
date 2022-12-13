package com.rewards.program.points.controller;
public class Util {
    public static int calcPoints(int dollars) {
        int points = 0;

        if(dollars<50) {
            return 0;
        }

        // 1 point for every dollar spent between $50 and $100 i

        if (dollars<=100) {
            points = 1*(dollars - 50);
        }

        // 2 points for every dollar spent over $100

        if (dollars>100) {
            points += 2*(dollars-100);
        }

        return points;
    }
}
