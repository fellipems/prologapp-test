package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import com.prologapp.test.interview.infra.dtos.CreateVehicleResponse;
import com.prologapp.test.interview.infra.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CreateVehicleResponse> create(@RequestBody CreateVehicleRequest newVehicle) {
        return ResponseEntity.ok(
                CreateVehicleResponse
                        .fromEntity(vehicleService.create(newVehicle))
        );
    }

}
