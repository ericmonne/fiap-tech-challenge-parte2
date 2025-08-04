package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

public class UserTypeNotFoundException extends RuntimeException {
    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
