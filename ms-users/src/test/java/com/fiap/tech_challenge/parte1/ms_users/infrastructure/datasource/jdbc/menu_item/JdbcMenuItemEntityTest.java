package com.fiap.tech_challenge.parte1.ms_users.infrastructure.datasource.jdbc.menu_item;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JdbcMenuItemEntityTest {

    @Test
    void testSettersAndGetters() {
        UUID id = UUID.randomUUID();
        String name = "Pizza Margherita";
        String description = "Pizza clássica com molho de tomate e mussarela";
        BigDecimal price = new BigDecimal("29.90");
        Boolean availableOnlyOnSite = true;
        String imagePath = "/images/pizza.jpg";
        UUID restaurantId = UUID.randomUUID();

        JdbcMenuItemEntity entity = new JdbcMenuItemEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        entity.setPrice(price);
        entity.setAvailableOnlyOnSite(availableOnlyOnSite);
        entity.setImagePath(imagePath);
        entity.setRestaurantId(restaurantId);

        assertEquals(id, entity.getId());
        assertEquals(name, entity.getName());
        assertEquals(description, entity.getDescription());
        assertEquals(price, entity.getPrice());
        assertEquals(availableOnlyOnSite, entity.getAvailableOnlyOnSite());
        assertEquals(imagePath, entity.getImagePath());
        assertEquals(restaurantId, entity.getRestaurantId());
    }

    @Test
    void testDefaultConstructor() {
        JdbcMenuItemEntity entity = new JdbcMenuItemEntity();

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getDescription());
        assertNull(entity.getPrice());
        assertNull(entity.getAvailableOnlyOnSite());
        assertNull(entity.getImagePath());
        assertNull(entity.getRestaurantId());
    }
}
