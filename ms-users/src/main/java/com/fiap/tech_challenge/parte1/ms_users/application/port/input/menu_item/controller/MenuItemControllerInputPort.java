package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.controller;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;

import java.util.List;
import java.util.UUID;

public interface MenuItemControllerInputPort {

    MenuItemResponseDTO create(CreateMenuItemDTO createMenuItemDTO);

    MenuItemResponseDTO update(UUID id, UpdateMenuItemDTO updateMenuItemDTO);

    MenuItemResponseDTO getById(UUID id);

    PaginatedResponseDTO<MenuItemResponseDTO> findAllPaginated(int size, int offset);

    List<MenuItemResponseDTO> findAll();

    void delete(UUID id);

    MenuItemResponseDTO changeAvailability(UUID id, Boolean availableOnlyOnSite);

    PaginatedResponseDTO<MenuItemResponseDTO> findByRestaurantId(MenuItemsByRestaurantRequestDTO request);
}
