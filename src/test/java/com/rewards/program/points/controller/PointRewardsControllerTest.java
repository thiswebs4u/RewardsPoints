package com.rewards.program.points.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;

//public class PointRewardsControllerTest {
    @WebMvcTest(controllers = PointController.class)
    @ActiveProfiles("test")
public class PointRewardsControllerTest {

        @Autowired
        private MockMvc mockMvc;

        //@MockBean
        //private UserService userService;

        private Transactions transactions;

        @BeforeEach
        void setUp() throws ParseException {
            transactions = new Transactions();

            transactions.getTransactions().add(new Transaction("joe", 90, "10/01/22"));
            transactions.getTransactions().add(new Transaction("joe", 90, "10/05/22"));
            transactions.getTransactions().add(new Transaction("joe", 100, "11/10/22"));
            transactions.getTransactions().add(new Transaction("joe", 100, "11/12/22"));
            transactions.getTransactions().add(new Transaction("joe", 120, "12/10/22"));
            transactions.getTransactions().add(new Transaction("joe", 120, "12/12/22"));
       }
    }


    /*
        {
            "customer": "joe",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "joe",
            "dollars": 90,
            "date": "10/01/22"
        },
        {
            "customer": "joe",
            "dollars": 100,
            "date": "11/10/22"
        },
        {
            "customer": "joe",
            "dollars": 100,
            "date": "11/12/22"
        },
        {
            "customer": "joe",
            "dollars": 120,
            "date": "12/10/22"
        },
        {
            "customer": "joe",
            "dollars": 120,
            "date": "12/12/22"
        },

    */

