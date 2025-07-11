package com.prologapp.test.interview.domain.entity;

import com.prologapp.test.interview.domain.enums.TireStatus;
import jakarta.persistence.*;

public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fire_number", nullable = false, unique = true)
    private String fireNumber;

    @Column(nullable = false)
    private String brand;

    @Column(name = "pressure_psi", nullable = false)
    private int pressurePsi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TireStatus status;

}
