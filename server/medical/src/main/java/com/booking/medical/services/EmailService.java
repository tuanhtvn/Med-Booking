package com.booking.medical.services;


public interface EmailService {
    void SendVerifyCode(String ToEmail, String UserName, String VerifyCode) throws Exception;

    // void SendEmailTicket(String ToEmail, TicketDto ticket)
}
