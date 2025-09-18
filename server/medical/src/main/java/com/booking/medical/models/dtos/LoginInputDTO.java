package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginInputDTO {
    @NotEmpty(message = "Vui lòng nhập địa chỉ Email")
    @JsonProperty("email")
    private String email;
    @NotEmpty(message = "Vui lòng nhập Mật khẩu")
    @JsonProperty("password")
    private String password;
}
