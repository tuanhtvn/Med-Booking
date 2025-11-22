package com.booking.medical.models.entities;

import com.booking.medical.common.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String birthday;
    private Gender gender;
    private String identificationCard;
    private String healthInsurance;
    private String phone;
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}