package com.booking.medical.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.booking.medical.models.entities.Ticket;

import jakarta.transaction.Transactional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE ticket", nativeQuery = true)
    void truncateTicketTable();
}
