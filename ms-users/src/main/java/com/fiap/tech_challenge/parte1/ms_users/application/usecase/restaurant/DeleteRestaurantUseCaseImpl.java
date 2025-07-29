package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.DeleteRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.RestaurantNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {

    private final RestaurantGateway restaurantGateway;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Transactional
    @Override
    public void execute(UUID restaurantId) {
        if (!restaurantGateway.existsById(restaurantId)) {
            throw new RestaurantNotFoundException("Restaurante não encontrado: " + restaurantId);
        }
        restaurantGateway.delete(restaurantId);
    }

}
