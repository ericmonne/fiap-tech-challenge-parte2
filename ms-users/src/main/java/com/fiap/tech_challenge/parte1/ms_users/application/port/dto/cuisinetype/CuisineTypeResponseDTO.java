package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype;

import java.util.UUID;

public record CuisineTypeResponseDTO(
        UUID id,
        String name
) {
}
