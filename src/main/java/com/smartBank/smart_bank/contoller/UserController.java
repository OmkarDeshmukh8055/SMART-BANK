package com.smartBank.smart_bank.contoller;

import com.smartBank.smart_bank.dto.BankResponse;
import com.smartBank.smart_bank.dto.CreditDebitRequest;
import com.smartBank.smart_bank.dto.EnquiryRequest;
import com.smartBank.smart_bank.dto.UserRequest;
import com.smartBank.smart_bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        BankResponse bankResponse = userService.balanceEnquiry(enquiryRequest);
        return new ResponseEntity<>(bankResponse,HttpStatus.FOUND);
    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        String s = userService.nameEnquiry(enquiryRequest);
        return new ResponseEntity<>(s,HttpStatus.FOUND);
    }

    @PostMapping("credit")
    public ResponseEntity<BankResponse> creditAmount(@RequestBody CreditDebitRequest request){
        BankResponse creditInfo = userService.creditAccount(request);

        return new ResponseEntity<>(creditInfo,HttpStatus.OK);

    }

    @PostMapping("debit")
    public ResponseEntity<BankResponse> debitAmount(@RequestBody CreditDebitRequest request){
        BankResponse debitInfo = userService.debitAccount(request);

        return new ResponseEntity<>(debitInfo,HttpStatus.OK);

    }
}
