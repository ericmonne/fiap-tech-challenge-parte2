package com.fiap.tech_challenge.parte1.ms_users.application.usecase.menu_item;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.MenuItemResponseDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.application.port.input.menu_item.usecase.UpdateMenuItemUseCase;
import com.fiap.tech_challenge.parte1.ms_users.application.port.mapper.menu_item.IMenuItemMapper;
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
    @Override
    public MenuItemResponseDTO execute(UUID id, UpdateMenuItemDTO dto) {
        MenuItem existingItem = menuItemGateway.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException(id));

        MenuItem menuItemToValidate = new MenuItem(
                id,
                dto.name(),
                dto.description(),
                dto.price(),
                dto.availableOnlyOnSite(),
                dto.imagePath(),
                existingItem.getRestaurantId()
        );

        menuItemValidators.forEach(validator -> validator.validate(menuItemToValidate));

        MenuItemUpdater.update(existingItem, dto);

        menuItemGateway.update(existingItem);

        return mapper.toResponseDTO(existingItem);
    }


}
