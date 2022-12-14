package com.rewards.program.points.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    PointsService pointsService;

    @PostMapping(value = "/calcPoints",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPoints(@RequestBody Transactions transactions) throws JsonProcessingException {
        Map<String, List<Transaction>> map = pointsService.transactionsByName(transactions);



        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(pointsService.buildResults(map));

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