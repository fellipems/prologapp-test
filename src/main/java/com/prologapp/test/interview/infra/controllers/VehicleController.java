package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {

    private VehicleRepository vehicleRepository;

    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    public ResponseEntity<Vehicle> getVehicleById() {
        return ResponseEntity.ok(new Vehicle());
    }

    public ResponseEntity<Vehicle> create() {
        return ResponseEntity.ok(new Vehicle());
    }

}
