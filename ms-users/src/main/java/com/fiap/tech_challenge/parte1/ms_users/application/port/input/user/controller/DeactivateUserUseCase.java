package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller;

import java.util.UUID;

public interface DeactivateUserUseCase {
    void execute(UUID id);
}
