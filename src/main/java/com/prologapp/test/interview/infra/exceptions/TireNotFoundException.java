package com.prologapp.test.interview.infra.exceptions;

public class TireNotFoundException extends RuntimeException {

    public TireNotFoundException(String message) {
        super(message);
    }

}
