package com.booking.medical.models.dtos;

import java.util.List;

import com.booking.medical.common.Gender;
import com.booking.medical.models.entities.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("gender")
    private Gender gender;
    @JsonProperty("specialist")
    private String specialist;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("schedules")
    private List<Schedule> schedule;
    @JsonProperty("deleted")
    private Boolean isDeleted;
}
