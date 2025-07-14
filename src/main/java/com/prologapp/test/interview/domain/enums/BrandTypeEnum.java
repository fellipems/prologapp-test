package com.prologapp.test.interview.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Tipo de marca se é marca de veículo ou marca de pneu",
        example = "VEHICLE"
)
public enum BrandTypeEnum {
    VEHICLE,
    TIRE
}
