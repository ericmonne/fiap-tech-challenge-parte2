package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.ReadPaginatedMenuItemsUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.infrastructure.mapper.MenuItemMapper;

import java.util.List;

/**
 * Use case responsible for retrieving a paginated list of menu items.
 * <p>
 * This class should be used in endpoints where paginated access to the restaurant's
 * menu items is required, typically for improving performance or supporting frontend pagination.
 * </p>
 * <p>
 * It interacts with the {@link MenuItemGateway} to fetch a limited subset of menu items based
 * on the given pagination parameters, and maps them to response DTOs using the {@link MenuItemMapper}.
 * <p>
 * Example use cases:
 * - Customer-facing endpoints that display parts of the menu
 * - Back-office panels with pageable grids or tables
 * - APIs that require efficient, lazy-loaded access to menu data
 * <p>
 * param size   the maximum number of items to retrieve
 * param offset the starting point for the items to retrieve
 * return a list of menu items as response DTOs within the given pagination window
 */
public class ReadPaginatedMenuItemsUseCaseImpl implements ReadPaginatedMenuItemsUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper menuItemMapper;

    public ReadPaginatedMenuItemsUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        this.menuItemGateway = menuItemGateway;
        this.menuItemMapper = menuItemMapper;
    }

    /**
     * Executes the use case to retrieve a paginated list of menu items.
     *
     * @param size   the maximum number of items to retrieve per page
     * @param offset the starting point for the items to retrieve
     * @return a PaginatedResponseDTO containing the list of menu items as response DTOs
     * within the given pagination window, along with pagination metadata
     */
    public PaginatedResponseDTO<MenuItemResponseDTO> execute(int size, int offset) {
        PaginatedResult<MenuItem> paginatedMenuItems = menuItemGateway.findPaginated(size, offset);

        List<MenuItemResponseDTO> items = paginatedMenuItems.content().stream()
                .map(menuItemMapper::toResponseDTO)
                .toList();

        return new PaginatedResponseDTO<>(
                items,
                paginatedMenuItems.totalItems(),
                paginatedMenuItems.totalPages(),
                offset / size,
                size
        );
    }
}
