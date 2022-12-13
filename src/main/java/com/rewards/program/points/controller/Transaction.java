package com.rewards.program.points.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class Transaction {
    private String customer;
    private int dollars;
    private Date date;
    public Transaction() {
    }
    public Transaction( String customer, int dollars, String date) throws ParseException {
        this.customer = customer;
        this.dollars = dollars;
        this.setDate(date);
    }
    public String getDate() {
        DateFormat fmt = new SimpleDateFormat("MM/dd/yy");

        return fmt.format(date);
    }

    public void setDate(String date) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("MM/dd/yy");
        this.date = fmt.parse(date);
    }
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getDollars() {
        return dollars;
    }

    public void setDollars(int dollars) {
        this.dollars = dollars;
    }

    public String getMonth() {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().getMonth().toString();
    }

}