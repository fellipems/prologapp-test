package com.prologapp.test.interview.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Status do pneu",
        example = "IN_USE"
)
public enum TireStatusEnum {
    IN_USE,
    AVAILABLE
}
