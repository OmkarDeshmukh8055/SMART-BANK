package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BankStatement {

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate);
}
