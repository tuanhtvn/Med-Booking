package com.booking.medical.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.medical.common.Response;
import com.booking.medical.models.dtos.CreateTicketInputDTO;
import com.booking.medical.services.TicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;
    private Response resp = new Response();

    @PostMapping("ticket/{doctorID}/{recordID}/{scheduleID}")
    public ResponseEntity<Response> Book(
            @PathVariable("doctorID") String doctorID,
            @PathVariable("recordID") String recordID,
            @PathVariable("scheduleID") String scheduleID,
            @RequestBody @Valid CreateTicketInputDTO createTicketInputDTO) {
        ticketService.Booking(doctorID, recordID, scheduleID, createTicketInputDTO);

        resp.setMessage("Bạn đã đặt lịch khám thành công");
        resp.setStatus(HttpStatus.OK);
        return ResponseEntity.status(resp.getStatus()).body(resp);
    }
}