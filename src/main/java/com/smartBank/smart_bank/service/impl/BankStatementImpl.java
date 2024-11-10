package com.smartBank.smart_bank.service.impl;

import com.smartBank.smart_bank.entity.Transaction;
import com.smartBank.smart_bank.repository.TransactionRepository;
import com.smartBank.smart_bank.service.BankStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankStatementImpl implements BankStatement {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) {
        LocalDate start=LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end=LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
//                .filter(transaction -> transaction.getCreatedAt().isAfter((start).atStartOfDay()))
//                .filter(transaction -> transaction.getCreatedAt().isBefore((end).atStartOfDay()))
                .toList();

        return transactions;
    }
}
