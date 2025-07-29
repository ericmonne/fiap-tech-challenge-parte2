package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import java.util.UUID;

public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }

    public MenuItemNotFoundException(UUID id){
        super("menu item not found for id: " + id);
    }
}
