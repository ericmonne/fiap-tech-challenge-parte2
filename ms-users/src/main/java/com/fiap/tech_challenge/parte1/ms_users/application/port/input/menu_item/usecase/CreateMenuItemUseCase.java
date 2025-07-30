package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;

public interface CreateMenuItemUseCase {

    MenuItemResponseDTO execute(CreateMenuItemDTO createMenuItemDTO);
}
