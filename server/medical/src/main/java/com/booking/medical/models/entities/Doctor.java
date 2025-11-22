package com.booking.medical.models.entities;

import com.booking.medical.common.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String title;
    private Gender gender;
    private String specialist;
    private Double price = 150.0;
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "doctor")
    private List<Schedule> schedules = new ArrayList<>();
}
