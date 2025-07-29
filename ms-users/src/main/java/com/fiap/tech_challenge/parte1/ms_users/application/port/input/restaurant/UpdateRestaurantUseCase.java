package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

public interface UpdateRestaurantUseCase {
    RestaurantResponseDTO execute(Restaurant restaurant);
}
