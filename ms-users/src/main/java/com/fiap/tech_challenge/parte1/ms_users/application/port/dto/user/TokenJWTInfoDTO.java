package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user;

/**
 * Data Transfer Object for carrying JWT token information.
 *
 * @param tokenJWT the JWT token string
 */
public record TokenJWTInfoDTO(String tokenJWT) {
}
