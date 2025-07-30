package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.UpdateMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.IMenuItemMapper;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemGateway;
import com.fiap.tech_challenge.parte1.ms_users.application.port.output.menu_item.MenuItemValidator;
import com.fiap.tech_challenge.parte1.ms_users.application.updater.MenuItemUpdater;
import com.fiap.tech_challenge.parte1.ms_users.domain.exception.MenuItemNotFoundException;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Use case for updating an existing menu item.
 */
public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final IMenuItemMapper mapper;
    private final List<MenuItemValidator> menuItemValidators;

    public UpdateMenuItemUseCaseImpl(
            MenuItemGateway menuItemGateway,
            IMenuItemMapper mapper,
            List<MenuItemValidator> validators
    ) {
        this.menuItemGateway = menuItemGateway;
        this.mapper = mapper;
        this.menuItemValidators = validators;
    }

    @Transactional
    public MenuItemResponseDTO execute(UUID id, UpdateMenuItemDTO updateMenuItemDTO) {
        // Find the existing menu item
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(
                        () -> new MenuItemNotFoundException(id)
                );

        MenuItem previewItem = mapper.toEntity(updateMenuItemDTO).withId(id);

        // Check if another menu item with the same name exists (excluding the current item)
        menuItemValidators.forEach(validator -> validator.validate(previewItem));

        MenuItemUpdater.update(existingItem, updateMenuItemDTO);

        menuItemGateway.update(existingItem);

        return mapper.toResponseDTO(existingItem);
    }

}
