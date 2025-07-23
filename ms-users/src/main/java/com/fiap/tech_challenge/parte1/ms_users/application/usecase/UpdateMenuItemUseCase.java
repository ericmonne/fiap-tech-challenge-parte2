package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.application.updater.MenuItemUpdater;
import com.fiap.tech_challenge.parte1.ms_users.dtos.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.dtos.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.ResourceNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.mappers.MenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case for updating an existing menu item.
 */
@Service
public class UpdateMenuItemUseCase {

    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    /**
     * Constructs a new UpdateMenuItemUseCase with the required dependencies.
     *
     * @param repository the menu item repository
     * @param mapper     the menu item mapper
     */
    public UpdateMenuItemUseCase(MenuItemRepository repository, MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Executes the use case to update an existing menu item.
     *
     * @param id         the ID of the menu item to update
     * @param updateMenuItemDTO the DTO containing the updated menu item data
     * @return the updated menu item as a response DTO
     * @throws ResourceNotFoundException      if no menu item is found with the given ID
     * @throws MenuItemAlreadyExistsException if a menu item with the same name already exists (excluding the current item)
     */
    @Transactional
    public MenuItemResponseDTO execute(UUID id, UpdateMenuItemDTO updateMenuItemDTO) {
        // Find the existing menu item
        MenuItem existingItem = repository.findById(id);
        if (existingItem == null) {
            throw new ResourceNotFoundException("Menu item not found with id: " + id);
        }

        // Check if another menu item with the same name exists (excluding the current item)
        validateMenuItemNameUniqueness(id, updateMenuItemDTO.name());

        // Update the menu item with new values
        updateMenuItemFromDTO(existingItem, updateMenuItemDTO);

        // Save the updated menu item
        repository.save(existingItem);

        return mapper.toResponseDTO(existingItem);
    }

    /**
     * Validates that no other menu item (except the one being updated) has the same name.
     *
     * @param currentId the ID of the current menu item being updated
     * @param newName   the new name to check for uniqueness
     * @throws MenuItemAlreadyExistsException if a menu item with the same name already exists
     */
    private void validateMenuItemNameUniqueness(UUID currentId, String newName) {
        repository.findAll().stream()
                .filter(item -> !item.getId().equals(currentId)) // Exclude current item
                .filter(item -> item.getName().equalsIgnoreCase(newName))
                .findFirst()
                .ifPresent(item -> {
                    throw new MenuItemAlreadyExistsException(
                            "A menu item with name '" + newName + "' already exists");
                });
    }

    /**
     * Updates a MenuItem entity with values from the request DTO.
     *
     * @param existingItem the menu item to update
     * @param updateMenuItemDTO          the request DTO with updated values
     */
    private void updateMenuItemFromDTO(MenuItem existingItem, UpdateMenuItemDTO updateMenuItemDTO) {
        MenuItemUpdater.update(existingItem, updateMenuItemDTO);
    }
}
