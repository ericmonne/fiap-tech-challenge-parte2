package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.address.AddressResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.cuisinetype.CuisineTypeResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.openinghour.OpeningHourResponseDTO;

import java.util.List;
import java.util.UUID;

public record RestaurantResponseDTO(
        UUID id,
        String name,
        AddressResponseDTO address,
        List<CuisineTypeResponseDTO> cuisineTypes,
        List<OpeningHourResponseDTO> openingHours,
        UUID userId
) {
}
