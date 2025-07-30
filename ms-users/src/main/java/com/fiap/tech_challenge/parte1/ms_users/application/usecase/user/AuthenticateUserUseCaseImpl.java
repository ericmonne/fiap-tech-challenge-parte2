package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticatedUser;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationDataDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationRequest;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.TokenJWTInfoDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.controller.AuthenticateUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.Authenticator;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final TokenProvider tokenProvider;
    private final Authenticator authenticator;

    public AuthenticateUserUseCaseImpl(TokenProvider tokenProvider, Authenticator authenticator) {
        this.tokenProvider = tokenProvider;
        this.authenticator = authenticator;
    }

    @Override
    public TokenJWTInfoDTO execute(AuthenticationDataDTO credentials) {
        AuthenticatedUser authenticatedUser = authenticator.authenticate(new AuthenticationRequest(credentials.login(), credentials.password()));
        String token = tokenProvider.generateToken(authenticatedUser.login());
        return new TokenJWTInfoDTO(token);
    }
}

