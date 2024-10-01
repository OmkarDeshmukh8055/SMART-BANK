package com.smartBank.smart_bank.service;

import com.smartBank.smart_bank.dto.EmailDetails;

public interface EmailService  {

    void sendEmail(EmailDetails emailDetails);
}
