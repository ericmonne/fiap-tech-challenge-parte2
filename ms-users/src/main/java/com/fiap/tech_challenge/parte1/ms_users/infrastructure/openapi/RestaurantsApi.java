package com.fiap.tech_challenge.parte1.ms_users.infrastructure.openapi;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.RestaurantRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.restaurant.RestaurantResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(RestaurantRoutes.RESTAURANTS_BASE)
public interface RestaurantsApi {

    @GetMapping(RestaurantRoutes.RESTAURANT_ID)
    @Operation(
            summary = "Busca",
            description = "Retorna os dados de um restaurante, pesquisado por ID",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestaurantResponseDTO.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema = @Schema(hidden = true)))
            }
    )
    default ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable("restaurantId") UUID restaurantId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping
    @Operation(
            summary = "Lista restaurantes",
            description = "Retorna uma lista paginada de restaurantes, conforme o usuário logado",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de restaurantes",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RestaurantResponseDTO.class))
                            )
                    )
            }
    )
    default ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurantsByUser(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PostMapping
    @Operation(
            summary = "Cadastro",
            description = "Registra um novo restaurante e retorna dados cadastrais",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "Restaurant field 'name' is required",
                                                "Restaurant must have at least one Address"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody @Valid RestaurantRequestDTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @PutMapping(RestaurantRoutes.RESTAURANT_ID)
    @Operation(
            summary = "Atualiza",
            description = "Atualiza os dados cadastrais de um restaurante, pesquisado por ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "timestamp": "2025-05-31T15:00:00Z",
                                              "status": 400,
                                              "errors": [
                                                "Restaurant field 'name' is required",
                                                "Restaurant must have at least one Address"
                                              ]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<RestaurantResponseDTO> updateRestaurant(
            @PathVariable("restaurantId") UUID restaurantId,
            @RequestBody @Valid RestaurantRequestDTO dto) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @DeleteMapping(RestaurantRoutes.RESTAURANT_ID)
    @Operation(
            summary = "Exclusão",
            description = "Exclui os dados cadastrais de um restaurante, pesquisado por ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant deleted successfully",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(hidden = true)))
    })
    default ResponseEntity<Void> deleteRestaurant(
            @PathVariable("restaurantId") UUID restaurantId) {
        throw new UnsupportedOperationException("Not implemented");
    }


/**
 * Retrieves a paginated list of menu items for a specific restaurant.
 *
 * @param restaurantId     The unique identifier of the restaurant.
 * @param size   The number of menu items to retrieve per page. Defaults to 10.
 * @param offset The starting position for the pagination. Defaults to 0.
 * @return A ResponseEntity containing a PaginatedResponseDTO with the list of MenuItemResponseDTO objects.
 *         Returns a 200 status if the retrieval is successful, 400 if the parameters are invalid,
 *         or 404 if the restaurant is not found.
 */
    @Operation(summary = "Get paginated menu items by restaurant ID",
            description = "Retrieves a paginated list of menu items for a specific restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved menu items"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })

    @GetMapping(RestaurantRoutes.ID_AND_MENU_ITEMS)
    default ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> getMenuItemsByRestaurantId(
            @Parameter(description = "ID of the restaurant")
            @PathVariable("restaurantId") UUID restaurantId,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Starting position")
            @RequestParam(defaultValue = "0") int offset) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
