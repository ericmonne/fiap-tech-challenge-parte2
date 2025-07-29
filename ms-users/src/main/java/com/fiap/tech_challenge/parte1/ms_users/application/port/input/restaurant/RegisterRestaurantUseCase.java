package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

public interface RegisterRestaurantUseCase {
    RestaurantResponseDTO execute(Restaurant restaurant);
}
