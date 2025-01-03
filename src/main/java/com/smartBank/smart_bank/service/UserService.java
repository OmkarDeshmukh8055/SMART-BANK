package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.dto.*;

public interface UserService {

   BankResponse createAccount(UserRequest userRequest);
  BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
  String nameEnquiry(EnquiryRequest enquiryRequest);
  BankResponse creditAccount(CreditDebitRequest request);
    BankResponse debitAccount(CreditDebitRequest request);
    BankResponse transfer(TransferRequest request);

  BankResponse login(LoginDto loginDto);
}
