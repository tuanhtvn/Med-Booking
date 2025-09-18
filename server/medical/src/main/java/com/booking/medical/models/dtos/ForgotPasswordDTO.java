package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordDTO {
    @JsonProperty("email")
    @Email
    private String email;

}