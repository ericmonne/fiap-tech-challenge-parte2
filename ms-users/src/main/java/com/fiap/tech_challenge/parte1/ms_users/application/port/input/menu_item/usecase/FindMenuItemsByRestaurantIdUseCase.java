package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;

public interface FindMenuItemsByRestaurantIdUseCase {
    PaginatedResponseDTO<MenuItemResponseDTO> execute(MenuItemsByRestaurantRequestDTO request);
}
