package com.fiap.tech_challenge.parte1.ms_users.application.port.output;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {

    UUID createUser(User user);

    Optional<User> findById(UUID userId);

}
