package com.siglo21.tfg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "COURT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURT_ID")
    private Long courtId;

    @Column(name = "COURT_NUMBER", nullable = false, unique = true)
    private Integer courtNumber;

    @Column(name = "COURT_NAME", nullable = false, length = 100)
    private String courtName;

    @Column(name = "SURFACE_TYPE", length = 50)
    private String surfaceType;

    @Column(name = "STATUS", length = 20)
    private String status;
}