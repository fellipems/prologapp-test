package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.TireStatusEnum;

public record CreateTireRequest(

        String fireNumber,
        Long brandId,
        int pressurePsi,
        TireStatusEnum status

) {}
