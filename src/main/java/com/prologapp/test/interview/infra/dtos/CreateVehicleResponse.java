package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;

public record CreateVehicleResponse(
        Long id,
        String plate,
        Long brandId,
        int mileage,
        VehicleStatusEnum status,
        int tiresQuantity
) {
    public static CreateVehicleResponse fromEntity(Vehicle vehicle) {
        return new CreateVehicleResponse(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand().getId(),
                vehicle.getMileage(),
                vehicle.getStatus(),
                vehicle.getTiresQuantity()
        );
    }
}
