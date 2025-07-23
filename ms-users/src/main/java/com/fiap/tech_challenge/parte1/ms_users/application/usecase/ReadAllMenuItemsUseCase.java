package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.mappers.MenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadAllMenuItemsUseCase {
    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    public ReadAllMenuItemsUseCase(MenuItemRepository repository, MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Executes the use case to retrieve all menu items.
     *
     * @return a list of menu items as response DTOs
     */
    public List<MenuItemResponseDTO> execute() {
        List<MenuItem> menuItems = repository.findAll();
        return mapper.mapList(menuItems);
    }
}
