package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;

import java.util.List;

public record VehicleWithTiresResponse(

        Long id,
        String plate,
        String brand,
        int mileage,
        VehicleStatusEnum status,
        int tiresQuantity,
        List<TireResponse> tires

) {}
