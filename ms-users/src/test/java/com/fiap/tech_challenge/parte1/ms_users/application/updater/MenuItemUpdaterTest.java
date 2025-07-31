package com.fiap.tech_challenge.parte1.ms_users.application.updater;

import com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item.UpdateMenuItemDTO;
import com.fiap.tech_challenge.parte1.ms_users.domain.model.MenuItem;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemUpdaterTest {

    @Test
    void update_shouldUpdateMenuItemFields_whenDtoHasNonNullFields() {

        MenuItem menuItem = new MenuItem();
        menuItem.setId(UUID.randomUUID());
        menuItem.setName("Old Name");
        menuItem.setDescription("Old Description");
        menuItem.setPrice(new BigDecimal("10.0"));
        menuItem.setAvailableOnlyOnSite(false);
        menuItem.setImagePath("old/path.jpg");
        menuItem.setRestaurantId(UUID.randomUUID());

        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(
                "New Name",
                "New Description",
                new BigDecimal("20.0"),
                true,
                "new/path.jpg"
        );

        // Executar atualização
        MenuItemUpdater.update(menuItem, dto);

        // Validar que os campos foram atualizados
        assertEquals("New Name", menuItem.getName());
        assertEquals("New Description", menuItem.getDescription());
        assertEquals(BigDecimal.valueOf(20.0), menuItem.getPrice());
        assertTrue(menuItem.getAvailableOnlyOnSite());
        assertEquals("new/path.jpg", menuItem.getImagePath());
    }

    @Test
    void update_shouldThrowException_whenAllFieldsNull() {
        MenuItem menuItem = new MenuItem();

        UpdateMenuItemDTO dto = new UpdateMenuItemDTO(null, null, null, null, null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MenuItemUpdater.update(menuItem, dto));

        assertEquals("No fields to update.", exception.getMessage());
    }
}
