package com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantDataSource {
    UUID createRestaurant(Restaurant restaurant);
    Optional<Restaurant> findById(UUID restaurantId);
    void update(Restaurant restaurant);
    boolean existsById(UUID restaurantId);
    void delete(UUID restaurantId);
    List<Restaurant> findAllRestaurantsByUserId(UUID userId, int size, int offset);
    boolean isRestaurantOwnedByUser(UUID restaurantId, UUID userId);
}
