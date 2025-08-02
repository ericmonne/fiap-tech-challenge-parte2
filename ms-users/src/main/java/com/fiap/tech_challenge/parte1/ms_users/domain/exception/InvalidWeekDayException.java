package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

public class InvalidWeekDayException extends RuntimeException {
    public InvalidWeekDayException(String message) {
        super(message);
    }
}
