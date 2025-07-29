package com.fiap.tech_challenge.parte1.ms_users.application.usecase.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.DeleteRestaurantUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.ForbiddenOperationException;
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
    public void execute(UUID restaurantId, UUID userId) {
        if (!restaurantGateway.existsById(restaurantId)) {
            throw new RestaurantNotFoundException("Restaurante não encontrado: " + restaurantId);
        }

        if (!restaurantGateway.isRestaurantOwnedByUser(restaurantId, userId)) {
            throw new ForbiddenOperationException("Você não tem permissão para atualizar este restaurante.");
        }

        restaurantGateway.delete(restaurantId);
    }

}
