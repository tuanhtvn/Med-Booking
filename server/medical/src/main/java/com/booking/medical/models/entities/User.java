package com.booking.medical.models.entities;

import com.booking.medical.common.BCryptConstants;
import com.booking.medical.common.Gender;
import com.booking.medical.common.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    private Role role = Role.USER;

    private Gender gender;

    private String birthday;

    private Boolean isDeleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verifycode_id", referencedColumnName = "id")
    private VerifyCode verifyCode;

    @OneToMany(mappedBy = "user")
    private List<Record> records;

    @OneToMany(mappedBy = "user")
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

}