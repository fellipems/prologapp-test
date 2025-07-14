package com.prologapp.test.interview.infra.dtos;

import com.prologapp.test.interview.domain.enums.TireStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateTireRequest(

        @Schema(
                description = "Número de fogo do pneu (identificador único)",
                example = "188",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String fireNumber,

        @Schema(
                description = "ID da marca do pneu (deve ser do tipo TIRE)",
                example = "3",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long brandId,

        @Schema(
                description = "Pressão atual do pneu em PSI (de 0 a 150)",
                example = "100",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "0",
                maximum = "150"
        )
        int pressurePsi,

        @Schema(
                description = "Status inicial do pneu",
                example = "AVAILABLE",
                requiredMode = Schema.RequiredMode.REQUIRED,
                implementation = TireStatusEnum.class
        )
        TireStatusEnum status
) {}
