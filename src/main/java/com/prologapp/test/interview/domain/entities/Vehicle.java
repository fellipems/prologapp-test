package com.prologapp.test.interview.domain.entities;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String plate;

    @Column(nullable = false)
    private Brand brand;

    @Column(nullable = false)
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatusEnum status;

    @Column(nullable = false)
    private int tiresQuantity;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleTire> tires;

}
