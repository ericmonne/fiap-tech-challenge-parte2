package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.ChangeMenuItemAvailabilityUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.UUID;

public class ChangeMenuItemAvailabilityUseCaseImpl implements ChangeMenuItemAvailabilityUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper menuItemMapper;

    public ChangeMenuItemAvailabilityUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        this.menuItemGateway = menuItemGateway;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public MenuItemResponseDTO execute(UUID id, Boolean availableOnlyOnSite) {
        MenuItem menuItem = menuItemGateway.findById(id).orElseThrow(() -> new MenuItemNotFoundException(id));

        if (availableOnlyOnSite == null) throw new IllegalArgumentException("availableOnlyOnSite cannot be null");

        menuItem.setAvailableOnlyOnSite(availableOnlyOnSite);
        menuItemGateway.update(menuItem);

        return menuItemMapper.toResponseDTO(menuItem);
    }
}
