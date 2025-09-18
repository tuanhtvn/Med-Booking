package com.booking.medical.models.entities;

import com.booking.medical.common.Gender;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "Doctors")
@Data
public class Doctor {
    @Id
    private String id;
    private String fullName;
    private String title;
    private Gender gender;
    private String specialist;
    private Double price = 150.0;
    @DBRef(lazy = true)
    private List<Schedule> schedule = new ArrayList<>();
    private Boolean isDeleted = false;

}
