package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.ChangePasswordCommand;

import java.util.UUID;

public interface ChangePasswordUserUseCase {
    void execute(UUID id, ChangePasswordCommand command);
}
