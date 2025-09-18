package com.booking.medical.models.entities;

import com.booking.medical.common.BCryptConstants;
import com.booking.medical.common.Gender;
import com.booking.medical.common.Role;

import java.time.Instant;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Document(collection = "Users")
@Data
@Slf4j
public class User {
    @Id
    private String id;
    private String fullName;
    @Indexed(unique = true)
    private String email;
    private String password;
    private Role role = Role.USER;
    private Gender gender;
    private String birthday;
    private Boolean isDeleted = false;
    private VerifyCode verifyCode = new VerifyCode();

    private List<Record> records;
    private List<Ticket> tickets;

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(BCryptConstants.ROUND));
    }

    public void setVerifyCode(String code) {
        if (code == null) {
            this.verifyCode.setCode(null);
            return;
        }
        this.verifyCode.setCode(code);
    }

    public boolean Verify(String code) {
        return this.verifyCode.Verify(code);
    }

    @Data
    private class VerifyCode {
        private String code;
        private Instant expiredAt;

        public void setCode(String code) {
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
    }
}
