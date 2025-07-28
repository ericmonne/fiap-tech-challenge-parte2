package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.CuisineType;

import java.util.List;
import java.util.UUID;

public record RestaurantResponseDTO(
        UUID id,
        String name,
        AddressResponseDTO address,
        CuisineType cuisineType,
        List<OpeningHourResponseDTO> openingHours,
        UUID userId
) {
}
