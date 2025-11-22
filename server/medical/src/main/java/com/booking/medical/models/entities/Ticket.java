package com.booking.medical.models.entities;

import java.time.Instant;

import com.booking.medical.common.StatusTicket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String serial;
    private Boolean isHealthInsurance;
    private Boolean isMorning;
    private StatusTicket status = StatusTicket.WAITING;
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Ticket [id=" + id + ", serial=" + serial + ", isHealthInsurance=" + isHealthInsurance + ", isMorning="
                + isMorning + ", status=" + status + ", schedule=" + schedule + ", createdAt=" + createdAt + "]";
    }
}