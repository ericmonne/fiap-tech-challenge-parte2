package com.fiap.tech_challenge.parte1.ms_users.infrastructure.security;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticatedUser;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationRequest;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.user.Authenticator;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuthenticator implements Authenticator {

    private final AuthenticationManager authenticationManager;

    public SpringSecurityAuthenticator(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticatedUser authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password())
        );

        User user = (User) auth.getPrincipal();
        return new AuthenticatedUser(user.getLogin(), user.getUserType().getName());
    }
}
