package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;


import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.DeleteMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Use case for deleting a menu item by its ID.
 */
@Service
public class DeleteMenuItemUseCaseImpl implements DeleteMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;

    public DeleteMenuItemUseCaseImpl(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
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
        if (!menuItemGateway.existsById(id)) {
            throw new ResourceNotFoundException("Menu item not found with id: " + id);
        }
        // Delete the item
        menuItemGateway.deleteById(id);
    }
}
