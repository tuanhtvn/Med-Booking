package com.booking.medical.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.medical.models.entities.Doctor;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {

}
