package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record VehicleResponse(

        @Schema(
                description = "ID do veículo",
                example = "10"
        )
        Long id,

        @Schema(
                description = "Placa do veículo",
                example = "ABC1234"
        )
        String plate,

        @Schema(
                description = "Nome da marca do veículo",
                example = "Volvo"
        )
        String brand,

        @Schema(
                description = "Quilometragem atual do veículo",
                example = "90000"
        )
        int mileage,

        @Schema(
                description = "Status do veículo",
                example = "ACTIVE",
                implementation = VehicleStatusEnum.class
        )
        VehicleStatusEnum status,

        @Schema(
                description = "Quantidade máxima de pneus suportados pelo veículo",
                example = "8"
        )
        int tiresQuantity
) {}