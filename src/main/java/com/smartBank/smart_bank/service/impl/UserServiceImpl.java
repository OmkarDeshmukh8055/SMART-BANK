package com.smartBank.smart_bank.service.impl;

import com.smartBank.smart_bank.config.JwtTokenProvider;
import com.smartBank.smart_bank.dto.*;
import com.smartBank.smart_bank.entity.Role;
import com.smartBank.smart_bank.entity.User;
import com.smartBank.smart_bank.repository.UserRepository;
import com.smartBank.smart_bank.service.TransactionService;
import com.smartBank.smart_bank.service.UserService;
import com.smartBank.smart_bank.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager manager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIT_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIT_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        User newUser= User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(0)
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .phoneNumber(userRequest.getPhoneNumber())
                .alternatePhoneNumber(userRequest.getAlternatePhoneNumber())
                .status("ACTIVE")
                .role(Role.valueOf("ROLE_ADMIN"))
                .build();

        User userSave=userRepository.save(newUser);

        EmailDetails emailDetails=EmailDetails.builder()
                        .recipient(userSave.getEmail())
                                .subject("Account Creation in SMART-BANK")
                                        .messageBody("Your account is successfully created in Smart Bank. \n welcome to SMART BANK Family.\n Account Number : "+userSave.getAccountNumber()+"\n Account Holder Name : "+userSave.getFirstName()+" "+userSave.getLastName())
                                                .build();
        emailService.sendEmail(emailDetails);

        return  BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                                        .accountInfo(AccountInfo.builder()
                                                .accountName(userSave.getFirstName()+" "+userSave.getLastName())
                                                .accountNumber(userSave.getAccountNumber())
                                                .accountBalance(userSave.getAccountBalance())
                                                .build())
                                        .build();
    }

    public BankResponse login(LoginDto loginDto){
        Authentication authentication=null;

        authentication=manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));

        EmailDetails emailDetails=EmailDetails.builder()
                .subject("Your Welcome!!!")
                .recipient(loginDto.getEmail())
                .messageBody("You logged in your account")
                .build();

        emailService.sendEmail(emailDetails);

        return BankResponse.builder()
                .responseCode("Logged in")
                .responseMessage(jwtTokenProvider.generateToken(authentication))
                .build();

    }
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isExits=userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isExits){
return BankResponse.builder()
        .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
        .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
        .accountInfo(null)
        .build();
        }

        User user=userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());

        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(user.getAccountNumber())
                        .accountName(user.getFirstName()+" "+user.getLastName())
                        .accountBalance(user.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExist=userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());

        if(!isAccountExist)
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        User foundUser=userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName()+" "+foundUser.getLastName();

    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isExits=userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isExits){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user=userRepository.findByAccountNumber(request.getAccountNumber());
        user.setAccountBalance(user.getAccountBalance()+request.getAmount());
        userRepository.save(user);

        TransactionDto transactionDto=TransactionDto.builder()
                .transactionType("CREDIT")
                .accountNumber(user.getAccountNumber())
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return  BankResponse.builder()
                        .responseCode(AccountUtils.AMOUNT_CREDITED_CODE)
                                .responseMessage(AccountUtils.AMOUNT_CREDITED_MESSAGE)
                                        .accountInfo(AccountInfo.builder()
                                                .accountNumber(user.getAccountNumber())
                                                .accountName(user.getFirstName()+" "+user.getLastName())
                                                .accountBalance(user.getAccountBalance())
                                                .build())
                .build();

    }

    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isExits=userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isExits){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user=userRepository.findByAccountNumber(request.getAccountNumber());
        if(user.getAccountBalance() < request.getAmount()){
            return BankResponse.builder()
                    .responseCode(AccountUtils.AMOUNT_INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.AMOUNT_INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        user.setAccountBalance(user.getAccountBalance()-request.getAmount());
        userRepository.save(user);

        TransactionDto transactionDto=TransactionDto.builder()
                .transactionType("DEBIT")
                .accountNumber(user.getAccountNumber())
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return  BankResponse.builder()
                .responseCode(AccountUtils.AMOUNT_DEBIT_CODE)
                .responseMessage(AccountUtils.AMOUNT_DEBIT_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(user.getAccountNumber())
                        .accountName(user.getFirstName()+" "+user.getLastName())
                        .accountBalance(user.getAccountBalance())
                        .build())
                .build();

    }

    @Override
    public BankResponse transfer(TransferRequest request) {

        if (!userRepository.existsByAccountNumber(request.getSourceAccountNumber())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.DEBIT_ACCOUNT_NOT_EXIT)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        if (!userRepository.existsByAccountNumber(request.getDestinationAccountNumber())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.CREDIT_ACCOUNT_NOT_EXIT)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User debit = userRepository.findByAccountNumber(request.getSourceAccountNumber());
        User credit = userRepository.findByAccountNumber(request.getDestinationAccountNumber());

        if(debit.getAccountBalance()<request.getAmount()){
            return BankResponse.builder()
                    .responseCode(AccountUtils.AMOUNT_INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.AMOUNT_INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        debit.setAccountBalance(debit.getAccountBalance()- request.getAmount());
        User saveDebit = userRepository.save(debit);

        EmailDetails debitEmail=EmailDetails.builder()
                .subject("Debit Alert")
                .recipient(debit.getEmail())
                .messageBody(request.getAmount()+" rs is transferred to "+request.getDestinationAccountNumber()+" account ! current balance "+saveDebit.getAccountBalance())
                .build();

        emailService.sendEmail(debitEmail);

        credit.setAccountBalance(credit.getAccountBalance()+request.getAmount());
        User saveCredit = userRepository.save(credit);

        EmailDetails creditEmail=EmailDetails.builder()
                .subject("Credit Alert")
                .recipient(debit.getEmail())
                .messageBody(request.getAmount()+" rs is credited from "+request.getSourceAccountNumber()+" account ! current balance "+saveCredit.getAccountBalance())
                .build();

        emailService.sendEmail(creditEmail);


        TransactionDto transactionDto=TransactionDto.builder()
                .transactionType("CREDIT")
                .accountNumber(credit.getAccountNumber())
                .amount(request.getAmount())
                .build();

        transactionService.saveTransaction(transactionDto);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSACTION_SUCCESSFULLY_COMPLETED_CODE)
                .responseMessage(AccountUtils.TRANSACTION_SUCCESSFULLY_COMPLETED_MESSAGE)
                .accountInfo(null)
                .build();
    }
}
