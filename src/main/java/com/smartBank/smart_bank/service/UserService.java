package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.dto.BankResponse;
import com.smartBank.smart_bank.dto.UserRequest;

public interface UserService {

  public BankResponse createAccount(UserRequest userRequest);
}
