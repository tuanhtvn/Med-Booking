package com.booking.medical.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.booking.medical.models.entities.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String>{
    
}
