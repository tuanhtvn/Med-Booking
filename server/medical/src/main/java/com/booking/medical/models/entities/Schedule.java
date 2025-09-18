package com.booking.medical.models.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Schedules")
@Data
public class Schedule {
    @Id
    private String id;
    private Instant date;
    private Integer countAm = 50;
    private Integer countPm = 50;
    private String clinic;
    private Boolean isDeleted = false;
}