package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

public class RestaurantNotFoundException extends RuntimeException {
    /**
     * Constructs a new RestaurantNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
