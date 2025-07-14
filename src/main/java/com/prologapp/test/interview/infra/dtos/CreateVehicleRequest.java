package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateVehicleRequest(

        @Schema(
                description = "Placa do veículo (7 ou 8 caracteres alfanuméricos)",
                example = "ABC1234",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        String plate,

        @Schema(
                description = "ID da marca do veículo (deve ser do tipo VEHICLE)",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        Long brandId,

        @Schema(
                description = "Quilometragem atual do veículo",
                example = "90000",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        int mileage,

        @Schema(
                description = "Status do veículo",
                example = "ACTIVE",
                requiredMode = Schema.RequiredMode.REQUIRED,
                implementation = VehicleStatusEnum.class
        )
        @NotNull
        VehicleStatusEnum status,

        @Schema(
                description = "Quantidade de pneus suportados pelo veículo",
                example = "8",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull
        int tiresQuantity
) { }
