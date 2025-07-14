package com.prologapp.test.interview.infra.exceptions;

public class TireAlreadyExistsWithGivenFireNumberException extends RuntimeException {
    public TireAlreadyExistsWithGivenFireNumberException(String message) {
        super(message);
    }
}
