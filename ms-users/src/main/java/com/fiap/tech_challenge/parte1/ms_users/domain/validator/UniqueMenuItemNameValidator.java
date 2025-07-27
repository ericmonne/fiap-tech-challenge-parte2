package com.fiap.tech_challenge.parte1.ms_users.domain.validator;

import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemAlreadyExistsException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class UniqueMenuItemNameValidator implements MenuItemValidator {
    private final MenuItemGateway menuItemGateway;

    public UniqueMenuItemNameValidator(MenuItemGateway menuItemGateway) {
        this.menuItemGateway = menuItemGateway;
    }


    /**
     * Validates that no other menu item with the same name exists.
     *
     * @param menuItem the menu item to validate
     * @throws MenuItemAlreadyExistsException if a menu item with the same name already exists
     */

    @Override
    public void validate(MenuItem menuItem) {
        menuItemGateway.findByName(menuItem.getName())
                .ifPresent(existingItem -> {
                    if (!existingItem.getId().equals(menuItem.getId())) {
                        throw new MenuItemAlreadyExistsException("A menu item with name '" + menuItem.getName() + "' already exists");
                    }
                });
    }
}