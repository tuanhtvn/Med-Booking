package com.booking.medical.models.entities;

import org.springframework.data.annotation.Id;

import com.booking.medical.common.Gender;

import lombok.Data;

@Data
public class Record {
    @Id
    private String id;
    private String fullName;
    private String birthday;
    private Gender gender;
    private String identificationCard;
    private String healthInsurance;
    private String phone;
    private String address;
}