package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.entities.Vehicle;
import com.prologapp.test.interview.domain.enums.VehicleStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateVehicleResponse(
        @Schema(
                description = "ID do veículo cadastrado",
                example = "10"
        )
        Long id,

        @Schema(
                description = "Placa do veículo",
                example = "ABC1234"
        )
        String plate,

        @Schema(
                description = "ID da marca do veículo",
                example = "1"
        )
        Long brandId,

        @Schema(
                description = "Quilometragem atual do veículo",
                example = "90000"
        )
        int mileage,

        @Schema(
                description = "Status do veículo",
                example = "INACTIVE",
                implementation = VehicleStatusEnum.class
        )
        VehicleStatusEnum status,

        @Schema(
                description = "Quantidade de pneus suportados pelo veículo",
                example = "8"
        )
        int tiresQuantity
) {
    public static CreateVehicleResponse fromEntity(Vehicle vehicle) {
        return new CreateVehicleResponse(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getBrand().getId(),
                vehicle.getMileage(),
                vehicle.getStatus(),
                vehicle.getTiresQuantity()
        );
    }
}
