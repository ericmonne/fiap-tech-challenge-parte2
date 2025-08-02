package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.user;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.UserType;

import java.util.List;
import java.util.UUID;

/**
 * Data Transfer Object representing a user response.
 * <p>
 * Contains user information returned from the system, including
 * user identifiers, contact details, role, and associated addresses.
 * </p>
 *
 * @param id      the unique identifier of the user
 * @param name    the full name of the user
 * @param email   the user's email address
 * @param login   the unique login identifier of the user
 * @param address the list of addresses associated with the user
 */
public record UsersResponseDTO(
        UUID id,
        String name,
        String email,
        String login,
        List<AddressResponseDTO> address) {
}
