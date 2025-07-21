package com.fiap.tech_challenge.parte1.ms_users.application.port.input;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;

public interface RegisterUserUseCase {
    User execute(User user);
}
