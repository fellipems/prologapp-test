package com.prologapp.test.interview.domain.entity;

import com.prologapp.test.interview.domain.enums.VehicleStatus;
import com.prologapp.test.interview.domain.enums.VehicleType;
import jakarta.persistence.*;

import java.util.List;

public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private int mileageKm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

    @Column(nullable = false)
    private Long typeId;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tire> tires;

}
