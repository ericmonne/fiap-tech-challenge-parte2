package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

/**
 * Exception thrown when an attempt is made to register or update a user
 * with an email that is already in use by another user.
 */
public class UserTypeDoesNotExistException extends RuntimeException {

    /**
     * Constructs a new EmailAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public UserTypeDoesNotExistException(String message) {
        super(message);
    }
}
