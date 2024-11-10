package com.smartBank.smart_bank.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public void commence(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // Log the error
        logger.error("Unauthorized access attempt: {}", authException.getMessage());

        // Set HTTP status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Optionally set response content type
        response.setContentType("application/json");

        // Create a custom JSON response
        String errorResponse = "{\n" +
                "  \"error\": \"Unauthorized\",\n" +
                "  \"message\": \"" + authException.getMessage() + "\"\n" +
                "}";

        // Send custom JSON response
        try (PrintWriter out = response.getWriter()) {
            out.print(errorResponse);
            out.flush();
        }
    }
}
