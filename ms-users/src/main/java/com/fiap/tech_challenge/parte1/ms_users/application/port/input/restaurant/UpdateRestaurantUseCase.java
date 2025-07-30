package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.UUID;

public interface UpdateRestaurantUseCase {
    RestaurantResponseDTO execute(UUID restaurantId, UUID userId, Restaurant restaurant);
}
