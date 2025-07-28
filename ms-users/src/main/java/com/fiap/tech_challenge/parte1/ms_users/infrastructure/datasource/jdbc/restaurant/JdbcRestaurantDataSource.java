package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.restaurant;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.restaurant.RestaurantDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.Restaurant;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.RestaurantMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcRestaurantDataSource implements RestaurantDataSource {

    private final JdbcRestaurantRepository jdbcRestaurantRepository;
    private final RestaurantMapper restaurantMapper;

    public JdbcRestaurantDataSource(JdbcRestaurantRepository jdbcRestaurantRepository, RestaurantMapper restaurantMapper) {
        this.jdbcRestaurantRepository = jdbcRestaurantRepository;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public UUID createRestaurant(Restaurant restaurant) {
        JdbcRestaurantEntity jdbcRestaurantEntity = restaurantMapper.toJdbcRestaurantEntity(restaurant);
        return jdbcRestaurantRepository.save(jdbcRestaurantEntity);
    }

    @Override
    public Optional<Restaurant> findById(UUID restaurantId) {
        return jdbcRestaurantRepository.findById(restaurantId);
    }

    @Override
    public void update(Restaurant restaurant) {
        JdbcRestaurantEntity jdbcRestaurantEntity = restaurantMapper.toJdbcRestaurantEntity(restaurant);
        jdbcRestaurantRepository.update(jdbcRestaurantEntity);
    }

    @Override
    public List<Restaurant> findAll(int size, int offset) {
        return jdbcRestaurantRepository.findAll(size, offset);
    }
}
