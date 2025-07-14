package com.prologapp.test.interview.infra.exceptions;

public class TireAlreadyLinkedException extends RuntimeException {
    public TireAlreadyLinkedException(String message) {
        super(message);
    }
}
