package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user;

import java.util.UUID;

public interface GetUserIdByLoginUseCase {
    UUID getUserIdByLogin(String login);
}
