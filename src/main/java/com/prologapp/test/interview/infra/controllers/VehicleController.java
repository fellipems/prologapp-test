package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.infra.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    public ResponseEntity<Vehicle> getVehicleById() {
        return ResponseEntity.ok(new Vehicle());
    }

    public ResponseEntity<Vehicle> create() {
        return ResponseEntity.ok(new Vehicle());
    }

}
