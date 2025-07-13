package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.TireStatusEnum;

public record TireResponse(

        Long id,
        String fireNumber,
        String brand,
        int pressurePsi,
        TireStatusEnum status,
        int positionId

) {}
