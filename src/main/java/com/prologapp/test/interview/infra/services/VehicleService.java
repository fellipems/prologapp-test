package com.prologapp.test.interview.infra.services;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.infra.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
}
