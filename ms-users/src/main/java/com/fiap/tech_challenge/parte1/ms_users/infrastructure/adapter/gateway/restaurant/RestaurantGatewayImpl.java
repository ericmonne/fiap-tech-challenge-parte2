package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantDataSource restaurantDataSource;

    public RestaurantGatewayImpl(RestaurantDataSource restaurantDataSource) {
        this.restaurantDataSource = restaurantDataSource;
    }

    @Override
    public UUID createRestaurant(Restaurant restaurant) {
        return restaurantDataSource.createRestaurant(restaurant);
    }

    @Override
    public Optional<Restaurant> findById(UUID restaurantId) {
        return restaurantDataSource.findById(restaurantId);
    }

    @Override
    public void update(Restaurant restaurant) {
        restaurantDataSource.update(restaurant);
    }

    public List<Restaurant> findAllRestaurantsByUserId(UUID userId, int size, int offset) {
        return restaurantDataSource.findAllRestaurantsByUserId(userId, size, offset);
    }

    @Override
    public boolean existsById(UUID restaurantId) {
        return restaurantDataSource.existsById(restaurantId);
    }

    @Override
    public void delete(UUID restaurantId) {
        restaurantDataSource.delete(restaurantId);
    }
}
