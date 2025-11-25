package com.booking.medical.models.entities;

import java.time.Instant;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

import com.booking.medical.common.BCryptConstants;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Data
@Slf4j
public class VerifyCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private Instant expiredAt;

    @OneToOne(mappedBy = "verifyCode")
    private User user;

    public void setCode(String code) {
        if (code == null) {
            return;
        }
        this.code = BCrypt.hashpw(code, BCrypt.gensalt(BCryptConstants.ROUND));
        this.expiredAt = Instant.now().plusSeconds(5 * 60);
    }

    public boolean Verify(String code) {
        if (this.expiredAt.isBefore(Instant.now())) {
            return false;
        }
        log.info(code);
        log.info(this.code);
        Boolean result = BCrypt.checkpw(code, this.code);
        log.info(result.toString());
        return BCrypt.checkpw(code, this.code);
    }

    public static String Get() {
        String verifyCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            verifyCode += random.nextInt(10);
        }
        return verifyCode;
    }
}