package com.smartBank.smart_bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankApplication.class, args);

        System.out.println("Smart bank is in progress");
    }
}