package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import org.springframework.dao.DataAccessException;

public class MenuItemPersistenceException extends PersistenceException {
    public MenuItemPersistenceException(String s) {
        super(s);
    }

    public MenuItemPersistenceException() {
        super("Menu item persistence exception");
    }

    public MenuItemPersistenceException(String s, DataAccessException e) {
        super(s, e);
    }

    public MenuItemPersistenceException(DataAccessException e) {
        super("Menu item persistence exception", e);
    }
}
