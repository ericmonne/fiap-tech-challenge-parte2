package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

public class OverlappingOpeningHourException extends RuntimeException {
    public OverlappingOpeningHourException(String message) {
        super(message);
    }
}
