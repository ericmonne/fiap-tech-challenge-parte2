package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;

import java.util.List;

public interface ReadAllMenuItemsUseCase {
    List<MenuItemResponseDTO> execute();
}
