package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype;

import jakarta.validation.constraints.NotBlank;

public record CuisineTypeRequestDTO(
        @NotBlank(message = "Cuisine type name is required")
        String name
) {
}
