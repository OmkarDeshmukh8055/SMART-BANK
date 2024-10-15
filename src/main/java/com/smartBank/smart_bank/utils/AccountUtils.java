package com.smartBank.smart_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIT_CODE="001";
        public static final String ACCOUNT_EXIT_MESSAGE="This user account already exist";
    public static final String ACCOUNT_CREATION_CODE="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Welcome to Smart Bank Family";
    public static final String ACCOUNT_NOT_EXIST_CODE="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE="Invalid Account Number";
    public static final String ACCOUNT_EXIST_CODE="004";
    public static final String ACCOUNT_FOUND_MESSAGE="Welcome to SMART-BANK Service";
    public static final String AMOUNT_CREDITED_CODE="005";
    public static final String AMOUNT_CREDITED_MESSAGE="Amount is credited successfully!!";
    public static final String AMOUNT_DEBIT_CODE="006";
    public static final String AMOUNT_DEBIT_MESSAGE="Amount is Debited successfully!!";
    public static final String AMOUNT_INSUFFICIENT_BALANCE_CODE="000";
    public static final String AMOUNT_INSUFFICIENT_BALANCE_MESSAGE="Balance is insufficient";
    public static final String DEBIT_ACCOUNT_NOT_EXIT="Please Your account number is wrong";
    public static final String CREDIT_ACCOUNT_NOT_EXIT="Please check account is not exits";
    public static final String TRANSACTION_SUCCESSFULLY_COMPLETED_CODE="200";
    public static final String TRANSACTION_SUCCESSFULLY_COMPLETED_MESSAGE="Transaction successfully completed";
    public static  String generateAccountNumber() {
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String year = String.valueOf(currentYear);
        String randomNumbers = String.valueOf(randomNumber);
        StringBuilder accountNumber = new StringBuilder();

        return  accountNumber.append(year).append(randomNumbers).toString();
    }

}
