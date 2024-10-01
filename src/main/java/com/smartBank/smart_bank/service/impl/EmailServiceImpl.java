package com.smartBank.smart_bank.service.impl;

import com.smartBank.smart_bank.dto.EmailDetails;
import com.smartBank.smart_bank.service.EmailService;
import org.slf4j.Logger;
import   org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

   private final static Logger logger=LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("{spring.mail.username}")
    private String senderMail;

    @Override
    public void sendEmail(EmailDetails emailDetails) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(senderMail);

            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMessageBody());
            mailMessage.setSubject(emailDetails.getSubject());

            javaMailSender.send(mailMessage);
            logger.info("Mail sent Successfully !!");

        }
        catch (MailException e)
        {
            throw new RuntimeException();
        }

    }
}
