package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;

public interface ReadPaginatedMenuItemsUseCase {
    PaginatedResponseDTO<MenuItemResponseDTO> execute(int size, int offset);
}
