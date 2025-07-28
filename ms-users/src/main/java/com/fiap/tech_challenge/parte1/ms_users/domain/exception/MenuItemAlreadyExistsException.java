package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
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

    public MenuItemAlreadyExistsException(MenuItem menuItem) {
        super("A menu item with name '" + menuItem.getName() + "' already exists");
    }
}
