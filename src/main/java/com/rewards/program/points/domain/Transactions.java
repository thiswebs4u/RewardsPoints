package com.rewards.program.points.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Transactions {
    private List<Transaction> allTransactions;

    public Transactions() {
        allTransactions = new ArrayList<>();
    }

    public List<Transaction> getAllTransactions() {
        return allTransactions;
    }

    public void setAllTransactions(List<Transaction> allTransactions) {
        this.allTransactions = allTransactions;
    }

    public void addTransaction(Transaction transaction) {
        allTransactions.add(transaction);
    }
}
