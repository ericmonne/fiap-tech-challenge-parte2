package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;


import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.CreateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.CreateMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.List;

public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper menuItemMapper;
    private final List<MenuItemValidator> menuItemValidators;

    public CreateMenuItemUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper, List<MenuItemValidator> menuItemValidators) {
        this.menuItemGateway = menuItemGateway;
        this.menuItemMapper = menuItemMapper;
        this.menuItemValidators = menuItemValidators;
    }

    @Override
    public MenuItemResponseDTO execute(CreateMenuItemDTO createMenuItemDTO) {
        // Convert DTO to domain model
        MenuItem menuItem = menuItemMapper.toEntity(createMenuItemDTO);

        // Validate the menu item using the registered validators
        validateMenuItem(menuItem);

        // Save the menu item using the gateway
        MenuItem savedMenuItem = menuItemGateway.save(menuItem);

        // Convert the saved domain model back to DTO and return
        return menuItemMapper.toResponseDTO(savedMenuItem);
    }

    private void validateMenuItem(MenuItem menuItem) {
        for (MenuItemValidator menuItemValidator : menuItemValidators) {
            menuItemValidator.validate(menuItem);
        }
    }

}
