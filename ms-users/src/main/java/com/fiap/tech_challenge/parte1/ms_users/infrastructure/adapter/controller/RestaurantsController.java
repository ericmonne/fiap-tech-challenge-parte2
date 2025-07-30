package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.controller.menu_item.MenuItemControllerInputPortImpl;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.restaurant.controller.RestaurantControllerInputPort;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi.RestaurantsApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final MenuItemControllerInputPort menuItemControllerInputPort;

    public RestaurantsController(RestaurantControllerInputPort restaurantControllerInputPort, MenuItemControllerInputPort menuItemControllerInputPort) {
        this.restaurantControllerInputPort = restaurantControllerInputPort;
        this.menuItemControllerInputPort = menuItemControllerInputPort;
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

    /**
     * Retrieves a paginated list of menu items for a specific restaurant.
     *
     * @param restaurantId The ID of the restaurant to filter menu items
     * @param size The number of items per page (default: 10, max: 50)
     * @param offset The starting position of the pagination (default: 0)
     * @return PaginatedResponseDTO containing the list of menu items and pagination metadata
     */
    @Operation(summary = "Get paginated menu items by restaurant ID",
            description = "Retrieves a paginated list of menu items for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved menu items"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> getMenuItemsByRestaurantId(
            @Parameter(description = "ID of the restaurant") @PathVariable UUID restaurantId,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Starting position") @RequestParam(defaultValue = "0") int offset) {

        MenuItemsByRestaurantRequestDTO request = new MenuItemsByRestaurantRequestDTO(restaurantId, size, offset);
        PaginatedResponseDTO<MenuItemResponseDTO> response =
                menuItemControllerInputPort
                        .findByRestaurantId(request);

        return ResponseEntity.ok(response);
    }
}
