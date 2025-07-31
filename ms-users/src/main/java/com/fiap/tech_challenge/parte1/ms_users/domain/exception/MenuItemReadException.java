package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.dao.DataAccessException;

public class MenuItemReadException extends DataAccessException {

    public MenuItemReadException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MenuItemReadException(MenuItem menuItem) {
        super("An error occurred while reading menu item with name " + menuItem.getName());
    }
}
