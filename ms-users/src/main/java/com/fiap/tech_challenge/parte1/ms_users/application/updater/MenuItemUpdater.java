package com.fiap.tech_challenge.parte1.ms_users.application.updater;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;

public class MenuItemUpdater {
    private MenuItemUpdater() {
    }

    /**
     * Updates the given MenuItem with the values from the given UpdateMenuItemDTO.
     *
     * @param menuItem the MenuItem to update
     * @param dto      the UpdateMenuItemDTO with the updated values
     */
    public static void update(MenuItem menuItem, UpdateMenuItemDTO dto) {
        if (!dto.allFieldsNull()) {
            menuItem.setName(dto.name());
            menuItem.setDescription(dto.description());
            menuItem.setPrice(dto.price());
            menuItem.setAvailableOnlyOnSite(dto.availableOnlyOnSite());
            menuItem.setImagePath(dto.imagePath());
        } else {
            throw new IllegalArgumentException("No fields to update.");
        }
    }
}
