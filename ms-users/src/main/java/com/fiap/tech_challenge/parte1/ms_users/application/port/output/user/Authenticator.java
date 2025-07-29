package com.fiap.tech_challenge.parte1.ms_users.application.port.output.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticationRequest;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user.AuthenticatedUser;

public interface Authenticator {
    AuthenticatedUser authenticate(AuthenticationRequest request);
}
