package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.FindMenuItemsByRestaurantIdUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.List;

public class FindMenuItemsByRestaurantIdUseCaseImpl implements FindMenuItemsByRestaurantIdUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper menuItemMapper;

    public FindMenuItemsByRestaurantIdUseCaseImpl(MenuItemGateway menuItemGateway, IMenuItemMapper menuItemMapper) {
        this.menuItemGateway = menuItemGateway;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public PaginatedResponseDTO<MenuItemResponseDTO> execute(MenuItemsByRestaurantRequestDTO request) {
        PaginatedResult<MenuItem> paginatedMenuItems = menuItemGateway.findByRestaurantId(request);

        List<MenuItemResponseDTO> menuItems = paginatedMenuItems.content().stream()
                .map(menuItemMapper::toResponseDTO)
                .toList();

        int size = request.size();
        int offset = request.offset();
        int currentPage = (size > 0 && offset >= 0) ? (offset / size) : 0;


        return new PaginatedResponseDTO<>(
                menuItems,
                paginatedMenuItems.totalItems(),
                paginatedMenuItems.totalPages(),
                currentPage,
                size
        );
    }
}
