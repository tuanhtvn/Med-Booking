package com.booking.medical.services;

import com.booking.medical.models.dtos.CreateTicketInputDTO;

public interface TicketService {
    void Booking(Long doctorID, Long recordID, Long scheduleID, CreateTicketInputDTO createTicketInputDTO);
}
