package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.ResourceNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.mappers.MenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for retrieving a single menu item by its ID.
 */
@Service
public class ReadMenuItemUseCase {

    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    /**
     * Constructs a new ReadMenuItemUseCase with the required dependencies.
     *
     * @param repository the menu item repository
     * @param mapper     the menu item mapper
     */
    public ReadMenuItemUseCase(MenuItemRepository repository, MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Executes the use case to retrieve a menu item by its ID.
     *
     * @param id the ID of the menu item to retrieve
     * @return the menu item as a response DTO
     * @throws ResourceNotFoundException if no menu item is found with the given ID
     */
    public MenuItemResponseDTO execute(UUID id) {
        MenuItem menuItem = repository.findById(id);
        if (menuItem == null) {
            throw new ResourceNotFoundException("Menu item not found");
        }

        return mapper.toResponseDTO(menuItem);
    }
}
