package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import jakarta.validation.constraints.NotNull;

public record CreateVehicleRequest(
        @NotNull
        String plate,
        @NotNull
        Long brandId,
        @NotNull
        int mileage,
        @NotNull
        VehicleStatusEnum status,
        @NotNull
        int tiresQuantity
) { }
