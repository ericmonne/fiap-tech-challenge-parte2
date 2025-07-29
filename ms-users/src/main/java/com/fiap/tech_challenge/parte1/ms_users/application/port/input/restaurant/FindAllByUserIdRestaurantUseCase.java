package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FindAllByUserIdRestaurantUseCase {
    List<RestaurantResponseDTO> execute(UUID userId, int size, int offset);
}
