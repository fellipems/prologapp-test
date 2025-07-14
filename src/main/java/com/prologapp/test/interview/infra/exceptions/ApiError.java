package com.prologapp.test.interview.infra.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        int status,
        String error,
        String message,
        @Schema(
                description = "Data e hora do erro no formato ISO-8601",
                example = "2024-07-13T22:08:49.123"
        )
        LocalDateTime timestamp
) {
    public static ApiError of(int status, String error, String message) {
        return new ApiError(status, error, message, LocalDateTime.now());
    }
}
