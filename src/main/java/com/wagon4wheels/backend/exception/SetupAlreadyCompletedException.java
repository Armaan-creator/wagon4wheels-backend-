package com.wagon4wheels.backend.exception;

public class SetupAlreadyCompletedException extends RuntimeException {
    public SetupAlreadyCompletedException(String message) {
        super(message);
    }
}
