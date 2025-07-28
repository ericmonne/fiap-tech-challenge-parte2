package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.ReadAllMenuItemsUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.MenuItemMapper;

import java.util.List;

/**
 * Use case responsible for retrieving all menu items from the system
 * This class should be used exclusively in endpoints that are restricted to users
 * with explicit permissions to view the restaurant's menu items.
 * <p>
 * It delegates the data retrieval to the {@link MenuItemGateway} and maps the domain entities
 * to response DTOs via the {@link MenuItemMapper}.
 * <p>
 * This use case is intended for administrative or internal use where the full list of menu items
 * is required without pagination.
 * <p>
 * Example scenarios:
 * - Internal dashboard for menu management
 * - Data export or backup operations
 * - Audit or reporting features
 */
public class ReadAllMenuItemsUseCaseImpl implements ReadAllMenuItemsUseCase {
    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper mapper;

    public ReadAllMenuItemsUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper mapper) {
        this.menuItemGateway = menuItemGateway;
        this.mapper = mapper;
    }

    /**
     * Executes the use case to retrieve all menu items.
     *
     * @return a list of menu items as response DTOs
     */
    public List<MenuItemResponseDTO> execute() {
        List<MenuItem> menuItems = menuItemGateway.findAll();
        return mapper.mapList(menuItems);
    }
}
