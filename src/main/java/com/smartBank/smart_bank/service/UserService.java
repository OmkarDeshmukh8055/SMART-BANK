package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.dto.BankResponse;
import com.smartBank.smart_bank.dto.CreditDebitRequest;
import com.smartBank.smart_bank.dto.EnquiryRequest;
import com.smartBank.smart_bank.dto.UserRequest;

public interface UserService {

   BankResponse createAccount(UserRequest userRequest);
  BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
  String nameEnquiry(EnquiryRequest enquiryRequest);
  BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
}
