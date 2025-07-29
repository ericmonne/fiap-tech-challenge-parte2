package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;

import java.util.UUID;

public interface FindByIdRestaurantUseCase {
    RestaurantResponseDTO execute (UUID restaurantId);
}
