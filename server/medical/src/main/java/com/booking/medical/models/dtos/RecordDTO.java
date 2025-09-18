package com.booking.medical.models.dtos;

import com.booking.medical.common.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RecordDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("birthday")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("identificationcard")
    private String identificationCard;
    @JsonProperty("healthinsurance")
    private String healthInsurance;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("address")
    private String address;
}
