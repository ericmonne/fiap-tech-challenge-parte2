package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.controller;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.MenuItemRoutes;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller.MenuItemControllerInputPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(MenuItemRoutes.MENU_ITEMS_BASE)
public class MenuItemController {

    private final MenuItemControllerInputPort menuItemControllerInputPort;

    public MenuItemController(MenuItemControllerInputPort menuItemControllerInputPort) {
        this.menuItemControllerInputPort = menuItemControllerInputPort;
    }


    /**
     * Handles the HTTP POST request for creating a new menu item.
     *
     * @param menuItemDTO the DTO containing the data for the menu item to be created
     * @param uriBuilder  the URI components builder to construct the location URI for the created resource
     * @return a ResponseEntity containing the created MenuItemResponseDTO and the location URI
     */
    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(
            @RequestBody @Valid CreateMenuItemDTO menuItemDTO,
            UriComponentsBuilder uriBuilder) {

        MenuItemResponseDTO createdItem = menuItemControllerInputPort.create(menuItemDTO);

        URI uri = uriBuilder.path(MenuItemRoutes.MENU_ITEMS_WITH_ID)
                .buildAndExpand(createdItem.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdItem);
    }

    @GetMapping(MenuItemRoutes.ID)
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable UUID id) {
        MenuItemResponseDTO menuItem = menuItemControllerInputPort.getById(id);
        return ResponseEntity.ok(menuItem);
    }

    /**
     * Handles the HTTP GET request for retrieving all menu items in the system.
     * <p>
     * This endpoint is intended for administrative or internal use where the full list of menu items
     * is required without pagination.
     * <p>
     * Example scenarios:
     * - Internal dashboard for menu management
     * - Data export or backup operations
     * - Audit or reporting features
     *
     * @return a ResponseEntity containing the list of menu items as response DTOs
     */
    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        List<MenuItemResponseDTO> menuItems = menuItemControllerInputPort.findAll();
        return ResponseEntity.ok(menuItems);
    }

    /**
     * Handles the HTTP GET request for retrieving a paginated list of menu items.
     *
     * @param size   the maximum number of items to retrieve per page
     * @param offset the starting point for the items to retrieve
     * @return a ResponseEntity containing a PaginatedResponseDTO with the list of menu items
     * as response DTOs within the given pagination window, along with pagination metadata
     */
    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<MenuItemResponseDTO>> getPaginatedMenuItems(@RequestParam(defaultValue = "10") int size,
                                                                                           @RequestParam(defaultValue = "0") int offset) {

        if (size < 0 || offset < 0) {
            return ResponseEntity.badRequest().build();
        }

        PaginatedResponseDTO<MenuItemResponseDTO> menuItems = menuItemControllerInputPort.findAllPaginated(size, offset);
        return ResponseEntity.ok(menuItems);
    }

    @PutMapping(MenuItemRoutes.ID)
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateMenuItemDTO updateDTO) {

        MenuItemResponseDTO updatedItem = menuItemControllerInputPort.update(id, updateDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping(MenuItemRoutes.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable UUID id) {
        menuItemControllerInputPort.delete(id);
    }

    @PutMapping(MenuItemRoutes.ID + "/change-availability")
    public ResponseEntity<MenuItemResponseDTO> changeMenuItemAvailability(@PathVariable UUID id, @RequestBody @Valid Boolean isAvailableOnSite) {
        MenuItemResponseDTO updatedItem = menuItemControllerInputPort.changeAvailability(id, isAvailableOnSite);
        return ResponseEntity.ok(updatedItem);
    }

}
