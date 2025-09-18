package com.booking.medical.models.dtos;

import com.booking.medical.common.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("birthday")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String birthday;
}
