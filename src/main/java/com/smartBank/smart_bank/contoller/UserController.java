package com.smartBank.smart_bank.contoller;

import com.smartBank.smart_bank.dto.BankResponse;
import com.smartBank.smart_bank.dto.UserRequest;
import com.smartBank.smart_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/createAccount")
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest){
        BankResponse account = userService.createAccount(userRequest);
        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }
}
