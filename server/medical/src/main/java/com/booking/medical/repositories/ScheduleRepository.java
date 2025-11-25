package com.booking.medical.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.booking.medical.models.entities.Schedule;

import jakarta.transaction.Transactional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE schedule", nativeQuery = true)
    void truncateScheduleTable();
}
