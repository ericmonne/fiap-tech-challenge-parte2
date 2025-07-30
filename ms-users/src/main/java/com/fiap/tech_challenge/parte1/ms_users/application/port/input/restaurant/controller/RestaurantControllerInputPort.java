package com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;

import java.util.List;
import java.util.UUID;

public interface RestaurantControllerInputPort {

    RestaurantResponseDTO getRestaurantById(UUID id);
    List<RestaurantResponseDTO> findAllRestaurantsByUser(UUID userId, int size, int page);
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto, UUID userId);
    RestaurantResponseDTO updateRestaurant(UUID id, RestaurantRequestDTO dto, UUID userId);
    void deleteRestaurant(UUID restaurantId, UUID userId);

}
