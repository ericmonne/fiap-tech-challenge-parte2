package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant;

import java.util.UUID;

public interface DeleteRestaurantUseCase {
    void execute(UUID restaurantId, UUID userId);
}
