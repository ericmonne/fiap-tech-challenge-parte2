package com.fiap.tech_challenge.parte1.ms_users.application.usecase;

import com.fiap.tech_challenge.parte1.ms_users.entities.MenuItem;
import com.fiap.tech_challenge.parte1.ms_users.repositories.MenuItemRepository;
import com.fiap.tech_challenge.parte1.ms_users.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case for deleting a menu item by its ID.
 */
@Service
public class DeleteMenuItemUseCase {

    private final MenuItemRepository repository;

    /**
     * Constructs a new DeleteMenuItemUseCase with the required dependencies.
     *
     * @param repository the menu item repository
     */
    public DeleteMenuItemUseCase(MenuItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Executes the use case to delete a menu item by its ID.
     *
     * @param id the ID of the menu item to delete
     * @throws ResourceNotFoundException if no menu item is found with the given ID
     */
    @Transactional
    public void execute(UUID id) {
        // First verify if the item exists
        MenuItem menuItem = repository.findById(id);
        if (menuItem == null) {
            throw new ResourceNotFoundException("Menu item not found with id: " + id);
        }
        
        // Delete the item
        repository.deleteById(id);

    }
}
