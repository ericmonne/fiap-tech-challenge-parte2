package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.usertype;

public record UserTypeResponseDTO(
        Long id,
        String name,
        String description,
        Boolean active
) {

}
