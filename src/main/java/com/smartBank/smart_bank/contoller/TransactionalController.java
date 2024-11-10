package com.smartBank.smart_bank.contoller;

import com.smartBank.smart_bank.entity.Transaction;
import com.smartBank.smart_bank.service.BankStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "api/transaction")
public class TransactionalController {

    @Autowired
    BankStatement bankStatement;
    @GetMapping("/statement")
    public ResponseEntity<List<Transaction>> getAccountStatement(@RequestParam String accountNumber,
                                                                 @RequestParam String startDate,
                                                                 @RequestParam String endDate
                                                                     )   {
        List<Transaction> transactions = bankStatement.generateStatement(accountNumber, startDate, endDate);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
