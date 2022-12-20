package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rewards.program.points.domain.Transaction;
import com.rewards.program.points.domain.Transactions;
import com.rewards.program.points.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Reward Points
 * <p>
 * Given a record of every transaction during a three month period,
 * calculate the reward points earned for each customer per month and total.
 * <p>
 * 1 point for every dollar spent between $50 and $100 i
 * <p>
 * 2 points for every dollar spent over $100
 * <p>
 * <p>
 * Get points for dollars spent
 * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points)
 */


@RestController
public class PointController {
    @Autowired
    PointsService pointsService;

    @PostMapping("/calcPoints")
    public ResponseEntity<String> getPoints(@RequestBody Transactions transactions) throws JsonProcessingException {
        Map<String, List<Transaction>> map = pointsService.getTransactionsByCustomerName(transactions);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(pointsService.buildResults(map));

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}