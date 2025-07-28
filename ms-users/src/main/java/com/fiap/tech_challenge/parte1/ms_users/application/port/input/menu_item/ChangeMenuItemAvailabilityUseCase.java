package com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;

import java.util.UUID;

public interface ChangeMenuItemAvailabilityUseCase {
    MenuItemResponseDTO execute(UUID id, Boolean availableOnlyOnSite);
}
