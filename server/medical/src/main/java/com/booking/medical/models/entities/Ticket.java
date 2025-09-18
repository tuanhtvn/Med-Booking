package com.booking.medical.models.entities;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.booking.medical.common.StatusTicket;

import lombok.Data;

@Data
public class Ticket {
    @Id
    private String id;
    private String serial;
    private Boolean isHealthInsurance;
    private Boolean isMorning;
    private StatusTicket status = StatusTicket.WAITING;
    @DBRef(lazy = true)
    private Schedule schedule;
    private Instant createdAt;
    @Override
    public String toString() {
        return "Ticket [id=" + id + ", serial=" + serial + ", isHealthInsurance=" + isHealthInsurance + ", isMorning="
                + isMorning + ", status=" + status + ", schedule=" + schedule + ", createdAt=" + createdAt + "]";
    }
}