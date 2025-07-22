package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationDataDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.TokenJWTInfoDTO;

public interface AuthenticateUserUseCase {
    TokenJWTInfoDTO execute(AuthenticationDataDTO credentials);
}

