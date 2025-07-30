package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemsByRestaurantRequestDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.paginated.PaginatedResult;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemDataSource;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemPersistenceException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JdbcMenuItemDataSource implements MenuItemDataSource {

    private final JdbcMenuItemRepository jdbcMenuItemRepository;
    private final IMenuItemMapper menuItemMapper;


    public JdbcMenuItemDataSource(JdbcMenuItemRepository jdbcMenuItemRepository, IMenuItemMapper menuItemMapper) {
        this.jdbcMenuItemRepository = jdbcMenuItemRepository;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public PaginatedResult<MenuItem> findAllPaginated(int size, int offset) {
        return jdbcMenuItemRepository.findAllPaginated(size, offset);
    }

    @Override
    public List<MenuItem> findAll() {
        return jdbcMenuItemRepository.findAll();
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        try {
            return jdbcMenuItemRepository.save(menuItemMapper.toJdbcMenuItemEntity(menuItem));
        } catch (DataAccessException e) {
            throw new MenuItemPersistenceException(e);
        }
    }

    @Override
    public Optional<MenuItem> findById(UUID id) {
        return jdbcMenuItemRepository.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        jdbcMenuItemRepository.deleteById(id);
    }

    @Override
    public void update(MenuItem menuItem) {
        JdbcMenuItemEntity jdbcMenuItemEntity = menuItemMapper.toJdbcMenuItemEntity(menuItem);
        jdbcMenuItemRepository.update(jdbcMenuItemEntity);
    }

    @Override
    public boolean existsById(UUID id) {
        return jdbcMenuItemRepository.existsById(id);
    }

    @Override
    public Optional<MenuItem> findByName(String name) {
        return jdbcMenuItemRepository.findByName(name);
    }

    @Override
    public PaginatedResult<MenuItem> findByRestaurantId(MenuItemsByRestaurantRequestDTO restaurantRequestDTO) {
        return jdbcMenuItemRepository.findByRestaurantId(restaurantRequestDTO);
    }
}
