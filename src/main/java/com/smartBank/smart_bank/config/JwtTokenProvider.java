package com.smartBank.smart_bank.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration}")
    private long jwtExpiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(generateKey())  // Using the renamed method
                .compact();
    }

    private Key generateKey() {
        byte[] bytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(generateKey()) // Using the renamed method
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(generateKey())  // Using the renamed method
                    .build()
                    .parse(token);
            return true;
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT token is expired: {}", ex.getMessage());
            throw ex;
        } catch (MalformedJwtException ex) {
            logger.error("JWT token is malformed: {}", ex.getMessage());
            throw ex;
        } catch (SignatureException ex) {
            logger.error("JWT signature validation failed: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("JWT token validation failed: {}", ex.getMessage());
            return false;
        }
    }
}
