package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChatInputDTO {
    @JsonProperty("message")
    @NotEmpty(message = "Vui lòng nhập câu hỏi")
    private String message;
}
