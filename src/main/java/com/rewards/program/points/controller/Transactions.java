package com.rewards.program.points.controller;

import java.util.ArrayList;
import java.util.List;

public class Transactions {
    List<Transaction> transactions = new ArrayList<Transaction>();

    Transactions() {

    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }


}
