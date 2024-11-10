package com.smartBank.smart_bank.contoller;

import com.smartBank.smart_bank.dto.*;
import com.smartBank.smart_bank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User management Api ")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/createAccount")
    @Operation(
            summary = "Create new Bank account",
            description = "Create the new bank account by providing some information"
    )
    @ApiResponse(
            responseCode = "201",
            description = "When account is created successfully then it will give a 201 code"
    )
    public ResponseEntity<BankResponse> createAccount(@RequestBody UserRequest userRequest){
        BankResponse account = userService.createAccount(userRequest);
        return new ResponseEntity<>(account,HttpStatus.CREATED);
    }

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        BankResponse bankResponse = userService.balanceEnquiry(enquiryRequest);
        return new ResponseEntity<>(bankResponse,HttpStatus.FOUND);
    }

    @PostMapping("/login")
    public BankResponse login(@RequestBody LoginDto loginDto){

        return userService.login(loginDto);

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

    @PostMapping("transfer")
    public ResponseEntity<BankResponse> transferAmountToAnotherAccount(@RequestBody TransferRequest request){
        BankResponse debitInfo = userService.transfer(request);

        return new ResponseEntity<>(debitInfo,HttpStatus.OK);

    }
}
