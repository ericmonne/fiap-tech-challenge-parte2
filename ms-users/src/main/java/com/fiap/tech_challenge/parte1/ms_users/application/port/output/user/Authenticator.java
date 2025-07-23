package com.fiap.tech_challenge.parte1.ms_users.application.port.output.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticationRequest;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.AuthenticatedUser;

public interface Authenticator {
    AuthenticatedUser authenticate(AuthenticationRequest request);
}
