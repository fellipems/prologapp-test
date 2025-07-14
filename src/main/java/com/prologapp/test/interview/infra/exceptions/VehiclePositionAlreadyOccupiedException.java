package com.prologapp.test.interview.infra.exceptions;

public class VehiclePositionAlreadyOccupiedException extends RuntimeException {

    public VehiclePositionAlreadyOccupiedException(String message) {
        super(message);
    }

}
