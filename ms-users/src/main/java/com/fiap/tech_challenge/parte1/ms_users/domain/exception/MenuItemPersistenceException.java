package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import org.springframework.dao.DataAccessException;

public class MenuItemPersistenceException extends PersistenceException {
    public MenuItemPersistenceException(String s) {
        super(s);
    }

    public MenuItemPersistenceException(DataAccessException e) {
        super("Failed to save menu item.", e);
    }
}
