package com.booking.medical.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String date;
    @JsonProperty("countAm")
    private Integer countAm;
    @JsonProperty("countPm")
    private Integer countPm;
    @JsonProperty("clinic")
    private String clinic;
    @JsonProperty("isDeleted")
    private Boolean isDeleted = false;
}
