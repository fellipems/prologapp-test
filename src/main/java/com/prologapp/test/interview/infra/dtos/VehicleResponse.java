package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;

public record VehicleResponse(

        Long id,
        String plate,
        String brand,
        int mileage,
        VehicleStatusEnum status,
        int tiresQuantity

) {}