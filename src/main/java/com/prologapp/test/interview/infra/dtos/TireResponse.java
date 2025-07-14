package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record TireResponse(

        @Schema(
                description = "ID do pneu",
                example = "5"
        )
        Long id,

        @Schema(
                description = "Número de fogo do pneu",
                example = "188"
        )
        String fireNumber,

        @Schema(
                description = "Nome da marca do pneu",
                example = "Pirelli"
        )
        String brand,

        @Schema(
                description = "Pressão atual do pneu em PSI",
                example = "100"
        )
        int pressurePsi,

        @Schema(
                description = "Status do pneu",
                example = "IN_USE",
                implementation = TireStatusEnum.class
        )
        TireStatusEnum status,

        @Schema(
                description = "Posição do pneu no veículo (caso esteja vinculado)",
                example = "2"
        )
        int positionId
) {}
