package com.fiap.tech_challenge.parte1.ms_users.infrastructure.adapter.gateway.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MenuItemGatewayImpl implements MenuItemGateway {
    private final MenuItemDataSource menuItemDataSource;

    public MenuItemGatewayImpl(MenuItemDataSource menuItemDataSource) {
        this.menuItemDataSource = menuItemDataSource;
    }


    @Override
    public MenuItem save(MenuItem menuItem) {
        return menuItemDataSource.save(menuItem);
    }

    @Override
    public void update(MenuItem menuItem) {
        menuItemDataSource.update(menuItem);
    }

    @Override
    public void deleteById(UUID id) {
        menuItemDataSource.deleteById(id);
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return menuItemDataSource.findById(id);
    }

    @Override
    public PaginatedResult<MenuItem> findPaginated(int size, int offset) {
        return menuItemDataSource.findAllPaginated(size, offset);
    }

    @Override
    public List<MenuItem> findAll() {
        return menuItemDataSource.findAll();
    }

    @Override
    public boolean existsById(UUID id) {
        return menuItemDataSource.existsById(id);
    }

    @Override
    public Optional<MenuItem> findByName(String name) {
        return menuItemDataSource.findByName(name);
    }

    @Override
    public PaginatedResult<MenuItem> findByRestaurantId(MenuItemsByRestaurantRequestDTO request) {
        return menuItemDataSource.findByRestaurantId(request);
    }
}
