package com.siglo21.tfg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "COURT_BOOKING")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "TIMESLOT_ID", nullable = false)
    private Long timeslotId;

    @Column(name = "BOOKING_DATE")
    private LocalDateTime bookingDate;

    @Column(name = "STATUS", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TIMESLOT_ID", insertable = false, updatable = false)
    private Timeslot timeslot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

}
