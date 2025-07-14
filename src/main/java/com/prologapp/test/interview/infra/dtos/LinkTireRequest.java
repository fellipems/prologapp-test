package com.prologapp.test.interview.infra.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record LinkTireRequest(

        @Schema(
                description = "ID do veículo ao qual o pneu será vinculado",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long vehicleId,

        @Schema(
                description = "ID do pneu a ser vinculado",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long tireId,

        @Schema(
                description = "Posição em que o pneu será instalado no veículo (deve estar entre 1 e a quantidade máxima de pneus do veículo)",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "1"
        )
        Integer positionId
) {}
