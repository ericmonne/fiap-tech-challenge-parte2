package com.fiap.tech_challenge.parte1.ms_users.domain.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(){
        super("resource not found");
    }
    public ResourceNotFoundException(UUID id){
        super("resource not found with id: " + id);
    }


}
