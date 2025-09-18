package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VerifyForgotPasswordDTO {
    @JsonProperty("password")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-+]).{8,20}$", message = "Mật khẩu phải chứa ít nhất 1 chữ số, 1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt và có độ dài từ 8-20 ký tự")
    private String password;

    @JsonProperty("confirmPassword")
    private String confirmPassword;

    @JsonProperty("verifycode")
    private String verifycode;
}
