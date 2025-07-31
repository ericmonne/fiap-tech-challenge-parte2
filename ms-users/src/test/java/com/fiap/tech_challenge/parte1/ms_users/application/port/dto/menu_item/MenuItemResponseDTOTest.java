package com.fiap.tech_challenge.parte1.ms_users.application.port.dto.menu_item;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemResponseDTOTest {

    @Test
    void shouldCreateMenuItemResponseDTOWithValidValues() {
        UUID id = UUID.randomUUID();
        String name = "Hambúrguer";
        String description = "Delicioso hambúrguer artesanal";
        BigDecimal price = new BigDecimal("29.90");
        Boolean availableOnlyOnSite = true;
        String imagePath = "/images/burger.png";
        UUID restaurantId = UUID.randomUUID();

        MenuItemResponseDTO dto = new MenuItemResponseDTO(
                id,
                name,
                description,
                price,
                availableOnlyOnSite,
                imagePath,
                restaurantId
        );

        assertEquals(id, dto.id());
        assertEquals(name, dto.name());
        assertEquals(description, dto.description());
        assertEquals(price, dto.price());
        assertEquals(availableOnlyOnSite, dto.availableOnlyOnSite());
        assertEquals(imagePath, dto.imagePath());
        assertEquals(restaurantId, dto.restaurantId());
    }

    @Test
    void shouldAllowNullDescriptionAndImagePath() {
        UUID id = UUID.randomUUID();
        String name = "Suco";
        BigDecimal price = new BigDecimal("9.50");
        Boolean availableOnlyOnSite = false;
        UUID restaurantId = UUID.randomUUID();

        MenuItemResponseDTO dto = new MenuItemResponseDTO(
                id,
                name,
                null,
                price,
                availableOnlyOnSite,
                null,
                restaurantId
        );

        assertNull(dto.description());
        assertNull(dto.imagePath());
    }
}
