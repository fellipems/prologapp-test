package com.prologapp.test.interview.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Status do veículo",
        example = "INACTIVE"
)
public enum VehicleStatusEnum {
    ACTIVE,
    INACTIVE
}
