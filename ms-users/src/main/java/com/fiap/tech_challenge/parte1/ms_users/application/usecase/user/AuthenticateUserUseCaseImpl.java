package com.fiap.tech_challenge.parte1.ms_users.application.usecase.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationDataDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.TokenJWTInfoDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.user.AuthenticateUserUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.token.TokenProvider;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public AuthenticateUserUseCaseImpl(AuthenticationManager authenticationManager,
                                       TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public TokenJWTInfoDTO execute(AuthenticationDataDTO credentials) {
        var authToken = new UsernamePasswordAuthenticationToken(
                credentials.login(), credentials.password()
        );
        Authentication auth = authenticationManager.authenticate(authToken);
        String token = tokenProvider.generateToken(((User) auth.getPrincipal()).getLogin());
        return new TokenJWTInfoDTO(token);
    }
}

