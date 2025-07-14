package com.prologapp.test.interview.infra.dtos;

public record LinkTireRequest(
        Long vehicleId,
        Long tireId,
        Integer positionId
) {}
