package com.prologapp.test.interview.infra.exceptions;

public class VehicleTireLimitExceededException extends RuntimeException {

    public VehicleTireLimitExceededException(String message) {
        super(message);
    }

}
