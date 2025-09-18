package com.booking.medical.services;

import com.booking.medical.models.dtos.CreateTicketInputDTO;

public interface TicketService {
    void Booking(String doctorID, String recordID, String scheduleID, CreateTicketInputDTO createTicketInputDTO);
}
