package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateTicketInputDTO {

    @JsonProperty("category")
    private Boolean isHealthInsurance;

    @JsonProperty("time")
    private Boolean isMorning;
}
