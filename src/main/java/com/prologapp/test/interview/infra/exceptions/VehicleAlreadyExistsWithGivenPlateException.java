package com.prologapp.test.interview.infra.exceptions;

public class VehicleAlreadyExistsWithGivenPlateException extends RuntimeException {

    public VehicleAlreadyExistsWithGivenPlateException(String message) {
        super(message);
    }

}
