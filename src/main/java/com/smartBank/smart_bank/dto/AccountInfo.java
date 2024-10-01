package com.smartBank.smart_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private  String accountName;
    private double accountBalance;
    private String accountNumber;
}
