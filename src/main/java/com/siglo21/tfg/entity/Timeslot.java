package com.siglo21.tfg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "TIMESLOT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {

    @Id
    @Column(name = "TIMESLOT_ID")
    private Long timeslotId;

    @Column(name = "COURT_ID", nullable = false)
    private Long courtId;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;

    @Column(name = "STATUS", length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURT_ID", insertable = false, updatable = false)
    private Court court;

}