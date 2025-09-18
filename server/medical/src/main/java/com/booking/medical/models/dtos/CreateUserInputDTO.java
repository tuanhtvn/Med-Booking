package com.booking.medical.models.dtos;

import com.booking.medical.common.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUserInputDTO {
    @NotEmpty(message = "Vui lòng nhập Họ và Tên")
    @JsonProperty("fullname")
    private String fullName;

    @NotEmpty(message = "Vui lòng nhập địa chỉ Email")
    @Email(message = "Vui lòng nhập đúng định dạng Email")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-+]).{8,20}$", message = "Mật khẩu phải chứa ít nhất 1 chữ số, 1 chữ thường, 1 chữ hoa, 1 ký tự đặc biệt và có độ dài từ 8-20 ký tự")
    @JsonProperty("password")
    private String password;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("birthday")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;
}
