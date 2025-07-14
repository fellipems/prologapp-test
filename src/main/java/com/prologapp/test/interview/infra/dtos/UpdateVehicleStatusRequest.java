package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateVehicleStatusRequest(
    @Schema(description = "Novo status do veículo", example = "INACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
    VehicleStatusEnum status
) {}
