package com.fiap.tech_challenge.parte1.ms_users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an attempt is made to create a menu item that already exists.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class MenuItemAlreadyExistsException extends RuntimeException {

    public MenuItemAlreadyExistsException(String message) {
        super(message);
    }

    public MenuItemAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MenuItemAlreadyExistsException() {
        super("A menu item with the given name already exists");
    }
}
