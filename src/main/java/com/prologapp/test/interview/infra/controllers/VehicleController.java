package com.prologapp.test.interview.infra.controllers;

import com.prologapp.test.interview.infra.dtos.CreateVehicleRequest;
import com.prologapp.test.interview.infra.dtos.CreateVehicleResponse;
import com.prologapp.test.interview.infra.dtos.VehicleResponse;
import com.prologapp.test.interview.infra.dtos.VehicleWithTiresResponse;
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

    @PostMapping
    public ResponseEntity<CreateVehicleResponse> create(@RequestBody CreateVehicleRequest newVehicle) {
        return ResponseEntity.ok(
                CreateVehicleResponse
                        .fromEntity(vehicleService.create(newVehicle))
        );
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleWithTiresResponse> getVehicleByIdWithTires(@PathVariable Long id) {

        return ResponseEntity.ok(vehicleService.findByIdWithTires(id));
    }

}
