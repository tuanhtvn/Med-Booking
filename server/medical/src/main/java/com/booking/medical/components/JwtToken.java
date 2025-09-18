package com.booking.medical.components;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.booking.medical.configs.EnvConfig;

@Component
public class JwtToken {
    @Autowired
    EnvConfig envConfig;
    @Autowired
    JwtEncoder jwtEncoder;

    public String getToken(String userId, String authorities) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(envConfig.getJwtExpiresTime(), ChronoUnit.DAYS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(userId)
                .claim("authorities", authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
