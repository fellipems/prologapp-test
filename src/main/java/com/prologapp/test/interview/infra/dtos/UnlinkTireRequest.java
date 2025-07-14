package com.prologapp.test.interview.infra.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record UnlinkTireRequest(

        @Schema(
                description = "ID do veículo do qual o pneu será desvinculado",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long vehicleId,

        @Schema(
                description = "ID do pneu a ser desvinculado",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long tireId
) {}
