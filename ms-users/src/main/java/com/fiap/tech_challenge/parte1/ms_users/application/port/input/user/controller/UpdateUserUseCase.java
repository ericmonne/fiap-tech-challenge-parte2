package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

public interface UpdateUserUseCase {
    User execute(User user);
}
