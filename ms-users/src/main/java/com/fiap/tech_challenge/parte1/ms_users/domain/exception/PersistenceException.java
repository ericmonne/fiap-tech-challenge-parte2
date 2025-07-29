package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import org.springframework.dao.DataAccessException;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String s) {
        super(s);
    }

    public PersistenceException(String s, DataAccessException e) {
        super(s, e);
    }
}
