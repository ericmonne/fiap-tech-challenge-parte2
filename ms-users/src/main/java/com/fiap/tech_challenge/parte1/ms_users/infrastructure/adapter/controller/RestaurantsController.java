package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi.RestaurantsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing restaurants.
 * <p>
 * Provides endpoints to create, update and retrieve restaurants.
 * </p>
 */
@RestController
public class RestaurantsController implements RestaurantsApi {

    private final RestaurantControllerInputPort restaurantControllerInputPort;

    public RestaurantsController(RestaurantControllerInputPort restaurantControllerInputPort) {
        this.restaurantControllerInputPort = restaurantControllerInputPort;
    }

    private UUID getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return UUID.fromString(authentication.getName());
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(UUID restaurantId) {
        return ResponseEntity.ok(restaurantControllerInputPort.getRestaurantById(restaurantId));
    }

    @Override
    public ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurantsByUser(int size, int page) {
        UUID userId = getLoggedUserId();
        return ResponseEntity.ok(restaurantControllerInputPort.findAllRestaurantsByUser(userId, size, page));
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(RestaurantRequestDTO dto) {
        UUID userId = getLoggedUserId();
        RestaurantResponseDTO response = restaurantControllerInputPort.createRestaurant(dto, userId);
        URI location = URI.create("/restaurants/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(UUID restaurantId, RestaurantRequestDTO dto) {
        UUID userId = getLoggedUserId();
        return ResponseEntity.ok(restaurantControllerInputPort.updateRestaurant(restaurantId, dto, userId));
    }

    @Override
    public ResponseEntity<Void> deleteRestaurant(UUID restaurantId) {
        UUID userId = getLoggedUserId();
        restaurantControllerInputPort.deleteRestaurant(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }
}
