package com.booking.medical.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.medical.models.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
