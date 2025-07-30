package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.CreateUserDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

public interface RegisterUserUseCase {
    CreateUserDTO execute(User user);
}
