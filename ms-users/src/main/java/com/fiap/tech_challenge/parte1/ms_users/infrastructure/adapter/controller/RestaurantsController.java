package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.RestaurantRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi.RestaurantsApi;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

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
    private final MenuItemControllerInputPort menuItemControllerInputPort;

    public RestaurantsController(RestaurantControllerInputPort restaurantControllerInputPort, MenuItemControllerInputPort menuItemControllerInputPort) {
        this.restaurantControllerInputPort = restaurantControllerInputPort;
        this.menuItemControllerInputPort = menuItemControllerInputPort;
    }

    private UUID getLoggedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return restaurantControllerInputPort.getUserIdByLogin(authentication.getName());
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(UUID restaurantId) {
        return ResponseEntity.ok(restaurantControllerInputPort.getRestaurantById(restaurantId));
    }

    @Override
    public ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurantsByUser(int size, int page) {
        UUID userId = getLoggedUserId();
        int offset = (page - 1) * size;
        if (offset < 0) offset = 0;
        List<RestaurantResponseDTO> list = restaurantControllerInputPort.findAllRestaurantsByUser(userId, size, offset);
        return ResponseEntity.ok(list);
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(RestaurantRequestDTO dto) {
        UUID userId = getLoggedUserId();
        RestaurantResponseDTO response = restaurantControllerInputPort.createRestaurant(dto, userId);
        URI location = URI.create(RestaurantRoutes.RESTAURANTS_BASE + "/" + response.id());
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

    @Override
    public ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> getMenuItemsByRestaurantId(
            UUID restaurantId,
            int size,
            int offset) {

        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, size, offset);
        PaginatedResponseDTO<MenuItemResponseDTO> response =
                menuItemControllerInputPort
                        .findByRestaurantId(request);

        return ResponseEntity.ok(response);
    }
}
