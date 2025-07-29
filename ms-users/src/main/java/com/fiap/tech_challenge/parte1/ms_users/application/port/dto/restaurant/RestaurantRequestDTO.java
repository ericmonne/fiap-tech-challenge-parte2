package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RestaurantRequestDTO(
        @NotBlank(message = "Restaurant field 'name' is required")
        String name,

        @Valid
        @NotNull(message = "Restaurant must have an Address")
        AddressRequestDTO address,

        @Valid
        @NotEmpty(message = "Restaurant must have at least one Opening Hour settled")
        List<OpeningHourRequestDTO> openingHours,

        @NotBlank(message = "Restaurant must have a Cuisine Type")
        CuisineType cuisineType
) {
}
