package com.smartBank.smart_bank.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIT_CODE="001";
    public static final String ACCOUNT_EXIT_MESSAGE="This user account already exist";
    public static final String ACCOUNT_CREATION_CODE="002";
    public static final String ACCOUNT_CREATION_MESSAGE="Welcome to Smart Bank Family";
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
