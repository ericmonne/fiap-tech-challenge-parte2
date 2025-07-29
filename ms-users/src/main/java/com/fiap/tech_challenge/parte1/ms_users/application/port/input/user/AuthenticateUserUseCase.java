package com.fiap.tech_challenge.parte1.ms_users.application.port.input.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticationDataDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.TokenJWTInfoDTO;

public interface AuthenticateUserUseCase {
    TokenJWTInfoDTO execute(AuthenticationDataDTO credentials);
}

