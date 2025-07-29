package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;

import java.util.UUID;

public interface UpdateMenuItemUseCase {
    MenuItemResponseDTO execute(UUID id, UpdateMenuItemDTO updateMenuItemDTO);
}
