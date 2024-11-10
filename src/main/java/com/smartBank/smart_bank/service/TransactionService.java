package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.dto.TransactionDto;

public interface TransactionService{

    void saveTransaction(TransactionDto transactionDto);
}
