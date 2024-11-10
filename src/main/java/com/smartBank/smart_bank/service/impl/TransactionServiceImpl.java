package com.smartBank.smart_bank.service.impl;

import com.smartBank.smart_bank.dto.TransactionDto;
import com.smartBank.smart_bank.entity.Transaction;
import com.smartBank.smart_bank.repository.TransactionRepository;
import com.smartBank.smart_bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {

        Transaction transaction=Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();

        transactionRepository.save(transaction);
    }
}
