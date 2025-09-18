package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UpdatePasswordDTO {

    @JsonProperty("oldPassword")
    private String oldPassword;

    @JsonProperty("password")
    private String password;
}
