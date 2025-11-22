package com.booking.medical.configs;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${JWT_EXPIRES_TIME}")
    private long jwtExpiresTime;

    @Value("${COOKIE_EXPIRES_TIME}")
    private int cookieExpriesTime;

    @Value("${app.jwt.rsa.public-key}")
    private RSAPublicKey publicKey;

    @Value("${app.jwt.rsa.private-key}")
    private RSAPrivateKey privateKey;

    @Value("${CLIENT_URL}")
    private String clientUrl;

    @Value("${SEEDER}")
    private Boolean seeder;

    public Boolean getSeeder() {
        return seeder;
    }

    public void setSeeder(Boolean seeder) {
        this.seeder = seeder;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public int getCookieExpriesTime() {
        return cookieExpriesTime;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public long getJwtExpiresTime() {
        return jwtExpiresTime;
    }

}
