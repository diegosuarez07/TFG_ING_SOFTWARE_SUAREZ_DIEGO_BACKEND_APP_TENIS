package com.siglo21.tfg.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "TOURNAMENT")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOURNAMENT_ID")
    private Long tournamentId;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CATEGORY", nullable = false, length = 20)
    private String category;

    @Column(name = "REGISTRATION_START_DATE", nullable = false)
    private LocalDate registrationStartDate;

    @Column(name = "REGISTRATION_END_DATE", nullable = false)
    private LocalDate registrationEndDate;

    @Column(name = "TOURNAMENT_START_DATE", nullable = false)
    private LocalDate tournamentStartDate;

    @Column(name = "TOURNAMENT_END_DATE", nullable = false)
    private LocalDate tournamentEndDate;

    @Column(name = "MAX_PLAYERS", nullable = false)
    private Integer maxPlayers;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

}
