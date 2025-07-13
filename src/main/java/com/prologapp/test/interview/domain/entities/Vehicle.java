package com.prologapp.test.interview.domain.entities;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]{7,8}$", message = "Placa deve conter de 7 a 8 caracteres, sendo eles caracteres alfanuméricos")
    private String plate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Column(nullable = false)
    @Min(0)
    private int mileage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatusEnum status;

    @Column(nullable = false)
    @Min(0)
    @Max(20)
    private int tiresQuantity;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<VehicleTire> tires;

}
