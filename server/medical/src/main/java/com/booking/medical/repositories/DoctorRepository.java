package com.booking.medical.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.medical.models.entities.Doctor;

import jakarta.transaction.Transactional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE doctor", nativeQuery = true)
    void truncateDoctorTable();
}
