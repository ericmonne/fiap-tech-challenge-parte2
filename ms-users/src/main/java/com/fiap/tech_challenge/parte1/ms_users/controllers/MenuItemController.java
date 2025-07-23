package com.fiap.tech_challenge.parte1.ms_users.controllers;

import com.fiap.tech_challenge.parte1.ms_users.api.routes.ApiRoutes;
import com.fiap.tech_challenge.parte1.ms_users.dtos.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.dtos.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.CreateMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.DeleteMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.ReadAllMenuItemsUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.ReadMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.usecase.UpdateMenuItemUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiRoutes.MENU_ITEMS_BASE)
public class MenuItemController {

    private final CreateMenuItemUseCase createMenuItemUseCase;
    private final ReadMenuItemUseCase readMenuItemUseCase;
    private final ReadAllMenuItemsUseCase readAllMenuItemsUseCase;
    private final UpdateMenuItemUseCase updateMenuItemUseCase;
    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Autowired
    public MenuItemController(
            CreateMenuItemUseCase createMenuItemUseCase,
            ReadMenuItemUseCase readMenuItemUseCase,
            ReadAllMenuItemsUseCase readAllMenuItemsUseCase,
            UpdateMenuItemUseCase updateMenuItemUseCase,
            DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.createMenuItemUseCase = createMenuItemUseCase;
        this.readMenuItemUseCase = readMenuItemUseCase;
        this.readAllMenuItemsUseCase = readAllMenuItemsUseCase;
        this.updateMenuItemUseCase = updateMenuItemUseCase;
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    @PostMapping
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(
            @RequestBody @Valid CreateMenuItemDTO menuItemDTO,
            UriComponentsBuilder uriBuilder) {

        MenuItemResponseDTO createdItem = createMenuItemUseCase.execute(menuItemDTO);

        URI uri = uriBuilder.path("/api/v1/menu-items/{id}")
                .buildAndExpand(createdItem.id())
                .toUri();

        return ResponseEntity.created(uri).body(createdItem);
    }

    @GetMapping(ApiRoutes.ID)
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable UUID id) {
        MenuItemResponseDTO menuItem = readMenuItemUseCase.execute(id);
        return ResponseEntity.ok(menuItem);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        List<MenuItemResponseDTO> menuItems = readAllMenuItemsUseCase.execute();
        return ResponseEntity.ok(menuItems);
    }

    @PutMapping(ApiRoutes.ID)
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateMenuItemDTO updateDTO) {

        MenuItemResponseDTO updatedItem = updateMenuItemUseCase.execute(id, updateDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping(ApiRoutes.ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable UUID id) {
        deleteMenuItemUseCase.execute(id);
    }
}
