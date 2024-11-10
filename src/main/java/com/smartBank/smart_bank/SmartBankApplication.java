package com.smartBank.smart_bank;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Smart Bank",
                description = "This is a fraud bank and created for learning purpose and which helps to jobless people for start the own business and pay loan whenever you want there is no limitations",
                version = "00",
                contact = @Contact(
                        name = "Omkar",
                        email = "omkardeshmukh65@gmail.com",
                        url = "xyz.com"
                        ),
                license = @License(
                        name = "Smart Bank",
                        url = "xyz.com"
                )
              ),
        externalDocs = @ExternalDocumentation(
                description = "Not availavle",
                url = "e=xyz.com"
        )
)
public class SmartBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankApplication.class, args);

        System.out.println("Smart bank is in progress");
    }
}
