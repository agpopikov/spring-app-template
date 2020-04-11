package org.template.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.template.model.auth.SecurityContextUserInfo;

import java.util.Optional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final String secret;

    public AuthService(@Value("${app.jwt-secret}") String secret) {
        this.secret = secret;
    }

    public Optional<SecurityContextUserInfo> validateAndGetUserInfo(String token) {
        try {
            Claims claims = (Claims) Jwts.parser().setSigningKey(secret.getBytes())
                    .parse(token)
                    .getBody();
            return Optional.of(new SecurityContextUserInfo(claims.getId()));
        } catch (SignatureException e) {
            log.warn("Failed to validate / process JWT token", e);
            return Optional.empty();
        }
    }
}
