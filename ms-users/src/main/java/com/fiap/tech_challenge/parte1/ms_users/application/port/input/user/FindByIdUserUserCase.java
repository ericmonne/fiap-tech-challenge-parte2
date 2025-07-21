package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.UUID;

public interface FindByIdUserUserCase {
    User execute(UUID id);
}
