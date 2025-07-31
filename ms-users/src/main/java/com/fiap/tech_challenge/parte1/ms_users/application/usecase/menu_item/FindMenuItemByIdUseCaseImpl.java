package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.FindMenuItemByIdUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.UUID;

public class FindMenuItemByIdUseCaseImpl implements FindMenuItemByIdUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper mapper;

    public FindMenuItemByIdUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper mapper) {
        this.menuItemGateway = menuItemGateway;
        this.mapper = mapper;
    }

    /**
     * Executes the use case to retrieve a menu item by its ID.
     *
     * @param id the ID of the menu item to retrieve
     * @return the menu item as a response DTO
     * @throws MenuItemNotFoundException if no menu item is found with the given ID
     */
    public MenuItemResponseDTO execute(UUID id) {
        MenuItem menuItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        return mapper.toResponseDTO(menuItem);
    }
}
