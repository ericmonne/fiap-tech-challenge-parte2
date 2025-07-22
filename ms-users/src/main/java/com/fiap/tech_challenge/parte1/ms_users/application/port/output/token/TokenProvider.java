package com.fiap.tech_challenge.parte1.ms_users.application.port.output.token;

public interface TokenProvider {
    String generateToken(String userLogin);

    String extractUserLoginFromToken(String token);
}

