package com.prologapp.test.interview.domain.entities;

import jakarta.persistence.*;

@Entity
public class VehicleTire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "tire_id")
    private Tire tire;

    @Column(nullable = false)
    private Long positionId;
}
