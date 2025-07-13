package com.prologapp.test.interview.domain.entities;

import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import jakarta.persistence.*;

@Entity
public class Tire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fire_number", nullable = false, unique = true)
    private String fireNumber;

    @Column(nullable = false)
    private Brand brand;

    @Column(name = "pressure_psi", nullable = false)
    private int pressurePsi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TireStatusEnum status;

}
