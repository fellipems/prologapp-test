package com.prologapp.test.interview.infra.exceptions;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public static ApiError of(int status, String error, String message) {
        return new ApiError(status, error, message, LocalDateTime.now());
    }
}
